package com.moonbolt.cityscale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;

public class Skill
{
	public String nameSkill;
	public String caster;
	public int posX;
	public int posY;
	public int height;
	public int width;
	public int timer;
	public int damage;
	public int areaSpreadX;
	public int areaSpreadY;
	public int castTime;
	public int delay;
	public int countFrameEffect;
	public boolean overEffect;
	public boolean isAreaSkill;
	public boolean following;
	public TextureAtlas atlas_tripleattack;
	public TextureAtlas atlas_icecrystal;
	public Sprite spr_master;
	
	public Skill() {
		atlas_tripleattack = new TextureAtlas(Gdx.files.internal("data/skills/tripleattack.txt"));
	}
	
	public Sprite CarregaEfeitoVisual(String nomeSkill, int countFrame) {
		
		if(nomeSkill.equals("tripleattack")) {
			spr_master = atlas_tripleattack.createSprite("tripleattack" + countFrame);
		}
		
		if(nomeSkill.equals("icecrystal")) {
			spr_master = atlas_icecrystal.createSprite("icecrystal" + countFrame);
		}
		
		
		return spr_master;
	}
	
	public static Skill RetornaDadosSKill(String nomeSkill, String usr) {
		Skill novaSkill = new Skill();
		
		if(nomeSkill == "tripleattack") {
			novaSkill.nameSkill = "tripleattack";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 10;
			novaSkill.castTime = 0;
			novaSkill.delay = 100;
			novaSkill.areaSpreadX = 0;
			novaSkill.areaSpreadY = 0;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = false;
			novaSkill.following = false;
		}
		
		if(nomeSkill == "icecrystal") {
			novaSkill.nameSkill = "icecrystal";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 50;
			novaSkill.castTime = 0;
			novaSkill.delay = 180;
			novaSkill.areaSpreadX = 0;
			novaSkill.areaSpreadY = 0;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = true;
			novaSkill.following = false;
		}
		
		if(nomeSkill == "fireball") {
			novaSkill.nameSkill = "fireball";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 50;
			novaSkill.castTime = 0;
			novaSkill.delay = 180;
			novaSkill.areaSpreadX = 0;
			novaSkill.areaSpreadY = 0;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = false;
			novaSkill.following = true;
		}
		
		if(nomeSkill == "heal") {
			novaSkill.nameSkill = "heal";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 10;
			novaSkill.castTime = 0;
			novaSkill.delay = 180;
			novaSkill.areaSpreadX = 0;
			novaSkill.areaSpreadY = 0;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = true;
			novaSkill.following = false;
		}
		
		return novaSkill;
	}
	
	public static boolean CheckMP(String nomeSkill, int MP) {
		if(nomeSkill.equals("tripleattack") && MP < 10) { return false; }		
		if(nomeSkill.equals("fireball") && MP < 23) { return false; }		
		if(nomeSkill.equals("icecrystal") && MP < 25) { return false; }		
		if(nomeSkill.equals("heal") && MP < 15) { return false; }		
		
		return true;
	}
	
	public boolean VerificaSkillArea(String nomeSkill){
		if(nomeSkill.equals("tripleattack")) { return false; }		
		if(nomeSkill.equals("fireball")) { return true; }		
		if(nomeSkill.equals("icecrystal")) { return true; }		
		if(nomeSkill.equals("heal")) { return true; }		
		
		return false;
	}
}


