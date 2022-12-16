package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.impl.model.TrackedTagServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;


import com.javamentor.qa.platform.service.impl.model.IgnoredTagServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.javamentor.qa.platform.models.dto.IgnoredTagDto;

@Log4j2
@RestController
@RequestMapping("/api/user/tag")
@AllArgsConstructor
@Api("ResourceTagController контроллер отвечающий за отслеживаемыйе теги пользователя")
public class ResourceTagController {
    private final TrackedTagServiceImpl trackedTagService;
    private final TagDtoService tagDtoService;
    private final IgnoredTagServiceImpl ignoredTagService;


    @ApiOperation("Метод добавлюящий тег в список ослеживаемых и возвращающий tagDto")
    @PostMapping("/{id}/tracked")
    public ResponseEntity<?> addTagToTrackedTag(@PathVariable(value = "id") Long id, @AuthenticationPrincipal User user) {
        if (trackedTagService.getById(id).isEmpty()) return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
        else {
            if (trackedTagService.getById(id).isEmpty()) {
                trackedTagService.addTagToTrackedTag(id, user);
                return new ResponseEntity<>(trackedTagService.getTagDto(id), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(id, HttpStatus.valueOf("The tag has already been added to the tracked"));
            }
        }
    }

    @ApiOperation("Метод добавлюящий тег в список игнорируемых и возвращающий tagDto")
    @PostMapping("/{id}/ignored/{user_id}")
    public ResponseEntity<?> addTagToIgnoredTag(@PathVariable(value = "id") Long id, @PathVariable(value = "user_id") Long userId) {
        if (ignoredTagService.getById(id).isEmpty()) {
            ignoredTagService.addTagToIgnoredTag(id, userId);
            return new ResponseEntity<>(ignoredTagService.getTagDto(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(id, HttpStatus.valueOf("The tag has already been added to the ignored"));
        }
    }


    @GetMapping("/ignored")
    @Operation(summary = "Вывод списка игнорируемых тегов пользователя")
    @ApiResponse(responseCode = "200", description = "Список игнорируемых тегов пользователя сформирован")

    public ResponseEntity<List<IgnoredTagDto>> getAllIgnoredTag() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(tagDtoService.getAllIgnoredTags(user.getId()));
    }

    @GetMapping("/related")
    @Operation(summary = "Лист, содержащий топ-10 тегов")
    @ApiResponse(responseCode = "200", description = "Лист содержащий топ-10 тегов выведен")

    public ResponseEntity<List<RelatedTagDto>> getTop10Tags() {
        return ResponseEntity.ok(tagDtoService.getTopTags());
    }
}


