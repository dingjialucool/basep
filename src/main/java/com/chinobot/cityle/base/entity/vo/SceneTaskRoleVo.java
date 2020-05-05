package com.chinobot.cityle.base.entity.vo;

import java.util.List;

import com.chinobot.cityle.base.entity.SceneTask;
import com.chinobot.cityle.base.entity.SceneTaskRole;

import lombok.Data;

@Data
public class SceneTaskRoleVo {
	private SceneTask sceneTask;
	private List<SceneTaskRole> sceneTaskRole;
}
