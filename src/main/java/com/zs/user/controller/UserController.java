package com.zs.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zs.user.common.BaseResponse;
import com.zs.user.common.ErrorCode;
import com.zs.user.common.ResultUtils;
import com.zs.user.exception.BusinessException;
import com.zs.user.model.domain.User;
import com.zs.user.model.domain.request.UserLoginRequest;
import com.zs.user.model.domain.request.UserRegisterRequest;
import com.zs.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.zs.user.constant.UserConstant.ADMIN_ROLE;
import static com.zs.user.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author zs
 * 用户接口
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {
  @Resource
    private UserService userService;

  /**
   * 用户注册
   * @param userRegisterRequest
   * @return
   */
  @PostMapping("/register")
  public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {

    //用户校验
    if (userRegisterRequest == null){
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
    String userAccount = userRegisterRequest.getUserAccount();
    String userPassword = userRegisterRequest.getUserPassword();
    String checkPassword = userRegisterRequest.getCheckPassword();
    String planetCode = userRegisterRequest.getPlanetCode();
    if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)){
     return null;    }
      long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
    return ResultUtils.success(result);
  }

  /**
   * 用户登录
   * @param userLoginRequest
   * @param request
   * @return
   */
  @PostMapping("/login")
  public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
    if (userLoginRequest == null){
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
    String userAccount = userLoginRequest.getUserAccount();
    String userPassword = userLoginRequest.getUserPassword();
    if (StringUtils.isAnyBlank(userAccount, userPassword)){
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
    User user = userService.userLogin(userAccount, userPassword, request);
    return ResultUtils.success(user);
  }

  /**
   * 用户注销
   * @param
   * @param request
   * @return
   */
  @PostMapping("/logout")
  public BaseResponse<Integer> userLogout(HttpServletRequest request) {
    if (request == null) {
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
    int result = userService.userLogout(request);
    return ResultUtils.success(result);

  }

  /**
   * 获取当前用户信息
   *
   * @param request
   * @return
   */
  @GetMapping("/current")
  public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
    Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
    User currentUser = (User) userObj;
    if (currentUser == null) {
      throw new BusinessException(ErrorCode.NOT_LOGIN);
    }
    long userId = currentUser.getId();
    // TODO 校验用户是否合法
    User user = userService.getById(userId);
    User safetyUser = userService.getSafetyUser(user);
    return ResultUtils.success(safetyUser);
  }
  /**
   * 查询
   * @param username
   * @param request
   * @return
   */
  @GetMapping("/search")
  public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request){
    if (!isAdmin(request)) {
      throw new BusinessException(ErrorCode.NO_AUTH, "缺少管理员权限");
    }
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    if (StringUtils.isNotBlank(username)){
      queryWrapper.like("username", username);
    }
    List<User> userList = userService.list(queryWrapper);

    List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
    return ResultUtils.success(list);
  }

  /**
   * 删除
   * @param id
   * @param request
   * @return
   */
  @PostMapping("/delete")
  public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
    if (!isAdmin(request)){
      throw new BusinessException(ErrorCode.NO_AUTH);
    }
    if(id <= 0){
      throw new BusinessException(ErrorCode.NO_AUTH);
    }
    boolean b = userService.removeById(id);
    return ResultUtils.success(b);
  }

  /**
   * 管理员权限
   * @param request 请求
   * @return 布尔类型对管理员的判断
   */
    private  boolean isAdmin (HttpServletRequest request){
      //仅管理员可以查询
      Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
      User user = (User) userObj;
      return user != null && user.getUserRole() == ADMIN_ROLE;
    }


}
