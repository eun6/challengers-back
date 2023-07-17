package org.knulikelion.challengers_backend.service.Impl;

import org.knulikelion.challengers_backend.data.dao.Impl.ProjectCrewDAOImpl;
import org.knulikelion.challengers_backend.data.dao.ProjectCrewDAO;
import org.knulikelion.challengers_backend.data.dao.ProjectDAO;
import org.knulikelion.challengers_backend.data.dto.request.ProjectCrewRequestDto;
import org.knulikelion.challengers_backend.data.dto.response.ProjectCrewResponseDto;
import org.knulikelion.challengers_backend.data.dto.response.ResultResponseDto;
import org.knulikelion.challengers_backend.data.entity.ProjectCrew;
import org.knulikelion.challengers_backend.data.repository.ProjectCrewRepository;
import org.knulikelion.challengers_backend.service.ProjectCrewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProjectCrewServiceImpl implements ProjectCrewService {
    private final ProjectDAO projectDAO;
    private final ProjectCrewDAO projectCrewDAO;

    public ProjectCrewServiceImpl(ProjectDAO projectDAO, ProjectCrewDAO projectCrewDAO) {
        this.projectDAO = projectDAO;
        this.projectCrewDAO = projectCrewDAO;
    }

    @Override
    public ResultResponseDto createProjectCrew(ProjectCrewRequestDto projectCrewRequestDto) {
        LocalDateTime currentTime = LocalDateTime.now();


        ProjectCrew projectCrew = new ProjectCrew();
        projectCrew.setProjectCrewName(projectCrewRequestDto.getName());
        projectCrew.setProjectCrewRole(projectCrewRequestDto.getRole());
        projectCrew.setProjectCrewPosition(projectCrewRequestDto.getPosition());
        projectCrew.setCreatedAt(currentTime);
        projectCrew.setUpdatedAt(currentTime);

        ProjectCrew createdProjectCrew = projectCrewDAO.createCrew(projectCrew);
        ResultResponseDto resultResponseDto = new ResultResponseDto();
        resultResponseDto.setCode(0);
        resultResponseDto.setMsg("팀원 생성 완료");

        return resultResponseDto;
    }

    @Override
    public Object getProjectCrewById(Long id) {
        if(projectCrewDAO.selectById(id).isEmpty()) {
            ResultResponseDto resultResponseDto = new ResultResponseDto();

            resultResponseDto.setCode(1);
            resultResponseDto.setMsg("팀원 조회 불가");

            return resultResponseDto;
        }else {
            ProjectCrew getProjectCrew = projectCrewDAO.selectById(id).get();
            ProjectCrewResponseDto projectCrewResponseDto = new ProjectCrewResponseDto();
            projectCrewResponseDto.setId(getProjectCrew.getId());
            projectCrewResponseDto.setName(getProjectCrew.getProjectCrewName());
            projectCrewResponseDto.setRole(getProjectCrew.getProjectCrewRole());
            projectCrewResponseDto.setPosition(getProjectCrew.getProjectCrewPosition());
            projectCrewResponseDto.setProjectId(projectCrewResponseDto.getProjectId());
            projectCrewResponseDto.setCreatedAt(LocalDateTime.parse(String.valueOf(getProjectCrew.getCreatedAt())));
            projectCrewResponseDto.setUpdatedAt(LocalDateTime.parse(String.valueOf(getProjectCrew.getUpdatedAt())));

            return projectCrewResponseDto;
        }
    }

    @Override
    public ResultResponseDto removeProjectCrew(Long id) {
        ResultResponseDto resultResponseDto = new ResultResponseDto();

        if(projectCrewDAO.selectById(id).isEmpty()) {
            resultResponseDto.setCode(1);
            resultResponseDto.setMsg("팀원 조회 불가");
        }else {
            projectCrewDAO.removeCrew(id);
            resultResponseDto.setCode(0);
            resultResponseDto.setMsg("팀원 삭제 완료");
        }
        return resultResponseDto;
    }
}
