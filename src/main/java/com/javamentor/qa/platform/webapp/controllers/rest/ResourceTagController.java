package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(" /api/user/tag")
public class ResourceTagController {


  private final TagDtoService tagDtoService;


  @GetMapping("/ignored")
  @Operation(summary = "Вывод списка игнорируемых тегов пользователя")
  @ApiResponse(responseCode = "200", description = "Список игнорируемых тегов пользователя сформирован")

  public ResponseEntity<List<IgnoredTagDto>> getAllIgnoredTag() {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(tagDtoService.getAllIgnoredTags(user.getId()));
  }
}


