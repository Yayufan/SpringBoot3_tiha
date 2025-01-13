package tw.org.organ.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tw.org.organ.service.UserService;


/**
 * <p>
 * 使用者表 前端控制器
 * </p>
 *
 * @author Joey
 * @since 2024-07-15
 */
@Tag(name = "一般用戶API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;	
	
	

}
