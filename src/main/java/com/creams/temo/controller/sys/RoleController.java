package com.creams.temo.controller.sys;

import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.sys.Role;
import com.creams.temo.entity.sys.UserEntity;
import com.creams.temo.entity.sys.request.RoleRequest;
import com.creams.temo.service.sys.RoleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@Api("RoleController Api")
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @ApiOperation("新增角色")
    @PostMapping(value = "/")
    public JsonResult addRole(@RequestBody RoleRequest role){
        try {
            if (roleService.addRole(role)){
                return new JsonResult("操作成功", 200, null, true);
            }else {
                return new JsonResult("操作失败", 500, null, false);
            }
        }catch (Exception e){
            return new JsonResult("操作失败", 500, null, false);
        }
    }

    @ApiOperation("修改角色")
    @PutMapping()
    public JsonResult updateRole(@RequestBody RoleRequest role){
        try {
            roleService.updateRole(role);
            return new JsonResult("操作成功", 200, null, true);
        }catch (Exception e){
            return new JsonResult("操作失败", 500, null, false);
        }
    }

    @ApiOperation("查询所有角色")
    @GetMapping("/{page}")
    public JsonResult queryRoles(@PathVariable(value = "page") Integer page){
        try {
            PageInfo<Role> pageInfo = roleService.queryRole(page);
            HashMap<String,Object> map = new HashMap<>();
            map.put("list", pageInfo.getList());
            map.put("total", pageInfo.getTotal());
            return new JsonResult("操作成功", 200, map, true);
        }catch (Exception e){
            return new JsonResult("操作失败", 500, null, false);
        }
    }

    @ApiOperation("查询该用户下的角色")
    @GetMapping("/{userId}/info")
    public JsonResult queryRolesByUserId(@PathVariable("userId") String userId){
        try {
            List<Role> roles = roleService.queryRoleByUserId(userId);
            return new JsonResult("操作成功", 200, roles, true);
        }catch (Exception e){
            return new JsonResult("操作失败", 500, null, false);
        }
    }

    @ApiOperation("更新角色状态")
    @PutMapping(value = "/{id}")
    public JsonResult setRoleStatus(@PathVariable(value = "id") @ApiParam("角色id") String roleId, Integer status){
        try {
            if (roleService.setRoleStatus(roleId, status)){
                return new JsonResult("操作成功", 200, null, true);
            }else {
                return new JsonResult("操作失败", 500, null, false);
            }
        }catch (Exception e){
            return new JsonResult("操作失败", 500, null, false);
        }
    }
}
