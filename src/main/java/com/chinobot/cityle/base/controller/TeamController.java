package com.chinobot.cityle.base.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.chinobot.common.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Dept;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.entity.vo.PersonRole;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.cityle.base.service.IDeptService;
import com.chinobot.cityle.base.service.IPersonService;
import com.chinobot.cityle.base.service.IRoleService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 队伍 前端控制器
 * </p>
 *
 * @author laihb
 * @since 2019-03-16
 */
@Api(tags = {"队伍类接口"})
@RestController
@RequestMapping("/api/base/team")
public class TeamController extends BaseController {

    @Autowired
    private IDeptService deptService;
    @Autowired
    private IPersonService personService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IFileBusService fileBusService;


    @ApiOperation(value = "部门 列表", notes = "参数 - Map param")
    @PostMapping("/deptList")
//	@RequestMapping("/deptList")
    public Result getDeptList(@RequestBody(required = false) Map<String, String> param) {
        String deptId = ThreadLocalUtil.getResources().getDept().getUuid();

        QueryWrapper<Dept> queryWrapperDept = new QueryWrapper<Dept>();
        queryWrapperDept.eq("uuid", deptId);
        Dept dept = deptService.getOne(queryWrapperDept);

        QueryWrapper<Dept> deptWrapper = new QueryWrapper<>();
        deptWrapper.orderByAsc("dcode");
        if (ThreadLocalUtil.isShenz()) {
            deptWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
        } else {
            deptWrapper.like("dept_code", dept.getDeptCode()).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
        }
        List<Dept> list = deptService.list(deptWrapper);
        return ResultFactory.success(deptService.list(deptWrapper));
    }


    @ApiOperation(value = "部门 分页列表", notes = "参数 - 分页page,Map param")
    @GetMapping("/deptAddrPage")
//	@RequestMapping("/deptAddrPage")
    public Result getDeptAddrPage(Page page, @RequestParam Map<String, String> param) {
        return ResultFactory.success(deptService.getDeptAddrPage(page, param));
    }


    @ApiOperation(value = "根据id获取部门/地址", notes = "参数 - 部门主键uuid")
    @PostMapping("/deptAddr")
//	@RequestMapping("/deptAddr")
    public Result getDeptAddr(@RequestParam String uuid) {
        Map result = new HashMap<>();
        result.put("deptAddr", deptService.getDeptAddrById(uuid));

        Map param = new HashMap<>();
        param.put("busId", uuid);
        param.put("url", GlobalConstant.IO_READ_IMAGE_PREFIX);
        param.put("module", "dept_album");
        result.put("files", fileBusService.getFileIdByBusId(param));

        return ResultFactory.success(result);
    }


    @ApiOperation(value = "部门 新增修改", notes = "参数 - 部门地址库包装类voAddressBase")
    @PostMapping("/deptEdit")
//	@RequestMapping("/deptEdit")
    public Result deptEdit(@RequestBody VoAddressBase<Dept> voAddressBase) {

        return deptService.editDept(voAddressBase);
    }


    @ApiOperation(value = "部门 删除", notes = "参数 - 部门类dept")
    @PostMapping("/deptDel")
//	@RequestMapping("/deptDel")
    public Result deptDel(@RequestBody Dept dept) {
//		dept.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
        deptService.delDept(dept.getUuid());
//		deptService.updateById(dept)
        return ResultFactory.success(true);
    }


    @ApiOperation(value = "人员 分页列表", notes = "参数 - Map param")
    @GetMapping("/personPage")
    public Result getPersonPage(Page page, @RequestParam Map<String, String> param) {

        String deptId = param.get("deptId");
        if (deptId == null || deptId == "") {
            param.put("deptId", ThreadLocalUtil.getResources().getDept().getUuid());
        }
        return ResultFactory.success(personService.getPersonPage(page, param));
    }


    @ApiOperation(value = "根据id获取人员", notes = "参数 - 人员主键uuid")
    @PostMapping("/person")
//	@RequestMapping("/person")
    public Result getPerson(@RequestParam(required = false) String uuid) {

        Map result = new HashMap();
        //角色列表
        result.put("role", roleService.getRoleList(new HashMap<>()));
        //人员信息
        Map param = new HashMap<>();
        param.put("uuid", uuid);
        param.put("url", "/aiuas" + GlobalConstant.IO_READ_IMAGE_PREFIX);
        param.put("module", "person_face");
        result.put("person", personService.getPersonByParam(param));
        //人员角色
        result.put("userRole", roleService.getRoleByPersonId(uuid));

        return ResultFactory.success(result);
    }


    @ApiOperation(value = "人员 新增修改", notes = "参数 - 人员角色类personRole")
    @PostMapping("/personEdit")
//	@RequestMapping("/personEdit")
    public Result personEdit(@RequestBody PersonRole personRole) {

        personService.saveOrUpdatePersonRole(personRole);

        return ResultFactory.success();
    }

    @ApiOperation(value = "判断用户密码是否输入正确", notes = "参数 - 人员角色personRole")
    @PostMapping("/passWordJudge")
//	@RequestMapping("/passWordJudge")
    public Result passWordJudge(@RequestBody PersonRole personRole) {
        Map result = new HashMap();
        result.put("passWordStatus", personService.passWordJudge(personRole));

        return ResultFactory.success(result);
    }


    @ApiOperation(value = "人员 删除", notes = "参数 - 人员类person")
    @PostMapping("/personDel")
//	@RequestMapping("/personDel")
    public Result personDel(@RequestBody Person person) {
        person.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);

        return ResultFactory.success(personService.updateById(person));
    }

    /**
     * @Author: shizt
     * @Date: 2020/3/5 18:23
     */
    @ApiOperation(value = "人员 列表", notes = "参数 - Map param")
    @GetMapping("/personList")
    public Result<List<Person>> getPersonList(@RequestParam Map<String, String> param) {
		LambdaQueryChainWrapper<Person> lambdaQueryChainWrapper = personService.lambdaQuery()
				.select(Person::getUuid, Person::getPname)
				.eq(Person::getDataStatus, GlobalConstant.DATA_STATUS_VALID);

		if(CommonUtils.objNotEmpty(param.get("duties"))){
			lambdaQueryChainWrapper.eq(Person::getDuties, param.get("duties"));
		}
		return ResultFactory.success(lambdaQueryChainWrapper.list());
    }
}
