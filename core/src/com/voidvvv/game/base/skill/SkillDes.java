package com.voidvvv.game.base.skill;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillDes {
    Class<? extends Skill> skillClass;
    String des;
}
