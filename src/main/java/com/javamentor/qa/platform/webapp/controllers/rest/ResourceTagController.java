package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/tag")
public class ResourceTagController {

    private final TagDtoService tagDtoService;

    @ApiOperation(value = "Получение топ 10 тэгов")

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список получен успешно"),
            @ApiResponse(code = 404, message = "Список не найден")
    })

    @GetMapping("/related")
    public ResponseEntity<List<RelatedTagDto>> getTop10Tags() {
        return tagDtoService.getTopTags().size() != 0 ?
                new ResponseEntity<>(tagDtoService.getTopTags(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
