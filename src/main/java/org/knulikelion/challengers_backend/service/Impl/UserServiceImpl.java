package org.knulikelion.challengers_backend.service.Impl;

import org.knulikelion.challengers_backend.data.dao.UserDAO;
import org.knulikelion.challengers_backend.data.dto.request.UserRequestDto;
import org.knulikelion.challengers_backend.data.dto.response.ResultResponseDto;
import org.knulikelion.challengers_backend.data.dto.response.UserResponseDto;
import org.knulikelion.challengers_backend.data.entity.User;
import org.knulikelion.challengers_backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Object getUserById(Long id) {
        logger.info("");
        if(userDAO.selectUserById(id).isEmpty()){
            ResultResponseDto resultResponseDto = new ResultResponseDto();

            resultResponseDto.setCode(1);
            resultResponseDto.setMsg("유저가 존재하지 않음");

            return resultResponseDto;
        }else{
            User selectedUser = userDAO.selectUserById(id).get();
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setId(selectedUser.getId());
            userResponseDto.setUserName(selectedUser.getUserName());
            userResponseDto.setEmail(selectedUser.getEmail());
            userResponseDto.setCreatedAt(String.valueOf(selectedUser.getCreatedAt()));
            userResponseDto.setUpdatedAt(String.valueOf(selectedUser.getUpdatedAt()));
            if(userDAO.getClubByUser(id)!=null){
                userResponseDto.setClubs(userDAO.getClubByUser(id));
            }else{
                userResponseDto.setClubs(null);
            }
            return userResponseDto;
        }
    }

    @Override
    public ResultResponseDto removeUser(Long id) {
        ResultResponseDto resultResponseDto = new ResultResponseDto();

        if(userDAO.selectUserById(id).isEmpty()){
            resultResponseDto.setCode(1);
            resultResponseDto.setMsg("유저가 존재하지 않음");
        }else{
            userDAO.removeUser(id);
            resultResponseDto.setCode(0);
            resultResponseDto.setMsg("유저 삭제");
        }
        return resultResponseDto;
    }

    @Override
    public ResultResponseDto createUser(UserRequestDto userRequestDto) {
        return null;
    }

    @Override
    public ResultResponseDto updateUser(Long id, UserRequestDto userRequestDto) throws Exception {
        LocalDateTime currentTime = LocalDateTime.now();

        Optional<User> selectedUser = userDAO.selectUserById(id);
        if(selectedUser.isEmpty()){
            ResultResponseDto resultResponseDto = new ResultResponseDto();
            resultResponseDto.setCode(1);
            resultResponseDto.setMsg("유저가 존재하지 않습니다.");
            return resultResponseDto;
        }

        User user = selectedUser.get();
        user.setUserName(userRequestDto.getUserName());
        user.setEmail(userRequestDto.getEmail());
        user.setUpdatedAt(currentTime);
        userDAO.updateUser(id, user);

        ResultResponseDto resultResponseDto = new ResultResponseDto();
        resultResponseDto.setCode(0);
        resultResponseDto.setMsg("유저 업데이트 완료");
        return resultResponseDto;
    }
}
