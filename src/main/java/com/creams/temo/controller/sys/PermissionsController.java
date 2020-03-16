package com.creams.temo.controller.sys;

import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.sys.Permissions;
import com.creams.temo.entity.sys.Role;
import com.creams.temo.entity.sys.request.PermissionsRequest;
import com.creams.temo.service.sys.PermissionsService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@Api("PermissionsController Api")
@RequestMapping("/permissions")
public class PermissionsController {

    @Autowired
    PermissionsService permissionsService;

    @ApiOperation("新增权限")
    @PostMapping(value = "/")
    public JsonResult addPermissions(@RequestBody PermissionsRequest permissions){
        try {
            if (permissionsService.addPermissions(permissions)){
                permissionsService.addPermissions(permissions);
                return new JsonResult("操作成功", 200, null, true);
            }else {
                return new JsonResult("操作失败", 500, null, false);
            }
        }catch (Exception e){
            return new JsonResult("操作失败", 500, null, false);
        }
    }

    @ApiOperation("修改权限")
    @PutMapping()
    public JsonResult updatePermissions(@RequestBody PermissionsRequest permissions){
        try {
            permissionsService.updatePermission(permissions);
            return new JsonResult("操作成功", 200, null, true);
        }catch (Exception e){
            return new JsonResult("操作失败", 500, null, false);
        }
    }

    @ApiOperation("查询所有权限")
    @GetMapping("/{page}")
    public JsonResult queryPermissions(@PathVariable(value = "page") Integer page){
        try {
            PageInfo<Permissions> pageInfo = permissionsService.queryPermissions(page);
            HashMap<String,Object> map = new HashMap<>();
            map.put("list", pageInfo.getList());
            map.put("total", pageInfo.getTotal());
            return new JsonResult("操作成功", 200, map, true);
        }catch (Exception e){
            return new JsonResult("操作失败", 500, null, false);
        }
    }

    @ApiOperation("查询该角色下的权限")
    @GetMapping("/{roleId}/info")
    public JsonResult queryPermissionsByRoleId(@PathVariable(value = "roleId") String roleId){
        try {
            List<Permissions> permissions = permissionsService.queryPermissions(roleId);
            return new JsonResult("操作成功", 200, permissions, true);
        }catch (Exception e){
            return new JsonResult("操作失败", 500, null, false);
        }
    }

    @ApiOperation("更新权限状态")
    @PutMapping("/{id}")
    public JsonResult setPermissionsStatus(@PathVariable(value = "id") String permissionsId, Integer status){
        try {
            if (permissionsService.setPermissionStatus(permissionsId, status)){
                return new JsonResult("操作成功", 200, null, true);
            }else {
                return new JsonResult("操作失败", 500, null, false);
            }
        }catch (Exception e){
            return new JsonResult("操作失败", 500, null, false);
        }
    }
}
