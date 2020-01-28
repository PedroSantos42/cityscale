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
	public TextureAtlas atlas_tripleattack;
	
	public TextureAtlas atlas_icecrystal;
	public TextureAtlas atlas_fireball;
	public TextureAtlas atlas_thundercloud;
	public TextureAtlas atlas_rockbound;
	public TextureAtlas atlas_soulclash;
	
	public TextureAtlas atlas_flysword;
	public TextureAtlas atlas_healthboost;
	public TextureAtlas atlas_havenblade;
	public TextureAtlas atlas_ironshield;
	public TextureAtlas atlas_protect;
	
	public TextureAtlas atlas_heal;
	public TextureAtlas atlas_atkboost;
	public TextureAtlas atlas_defboost;
	public TextureAtlas atlas_regen;
	public TextureAtlas atlas_holyprism;
	
	public TextureAtlas atlas_bulletrain;
	public TextureAtlas atlas_lockshot;
	public TextureAtlas atlas_precision;
	public TextureAtlas atlas_mine;
	public TextureAtlas atlas_fastshot;
	
	public TextureAtlas atlas_invisibility;
	public TextureAtlas atlas_poisonhit;
	public TextureAtlas atlas_dashkick;
	public TextureAtlas atlas_steal;
	public TextureAtlas atlas_doublehit;
	
	public TextureAtlas atlas_hammercrash;
	public TextureAtlas atlas_ragebound;
	public TextureAtlas atlas_overpower;
	public TextureAtlas atlas_berserk;
	public TextureAtlas atlas_impound;
	
	//public TextureAtlas atlas
	
	
	public Sprite spr_master;
	
	public Skill() {
		//Novice
		atlas_tripleattack = new TextureAtlas(Gdx.files.internal("data/skills/tripleattack.txt"));
		
		//Mage
		atlas_icecrystal = new TextureAtlas(Gdx.files.internal("data/skills/icecrystal.txt"));
		atlas_fireball = new TextureAtlas(Gdx.files.internal("data/skills/fireball.txt"));
		atlas_thundercloud = new TextureAtlas(Gdx.files.internal("data/skills/thundercloud.txt"));
		atlas_rockbound = new TextureAtlas(Gdx.files.internal("data/skills/rockbound.txt"));
		atlas_soulclash = new TextureAtlas(Gdx.files.internal("data/skills/soulclash.txt"));
		
		//Swordman
		atlas_flysword = new TextureAtlas(Gdx.files.internal("data/skills/flysword.txt"));
		atlas_healthboost = new TextureAtlas(Gdx.files.internal("data/skills/healthboost.txt"));
		atlas_havenblade = new TextureAtlas(Gdx.files.internal("data/skills/havenblade.txt"));
		atlas_ironshield = new TextureAtlas(Gdx.files.internal("data/skills/ironshield.txt"));
		atlas_protect = new TextureAtlas(Gdx.files.internal("data/skills/protect.txt"));
		
		//Medic
		atlas_heal = new TextureAtlas(Gdx.files.internal("data/skills/flysword.txt"));
		atlas_atkboost = new TextureAtlas(Gdx.files.internal("data/skills/atkboost.txt"));
		atlas_defboost = new TextureAtlas(Gdx.files.internal("data/skills/defboost.txt"));
		atlas_regen = new TextureAtlas(Gdx.files.internal("data/skills/regen.txt"));
		atlas_holyprism = new TextureAtlas(Gdx.files.internal("data/skills/holyprism.txt"));
		
		//Gunner
		atlas_bulletrain = new TextureAtlas(Gdx.files.internal("data/skills/bulletrain.txt"));
		atlas_lockshot = new TextureAtlas(Gdx.files.internal("data/skills/lockshot.txt"));
		atlas_precision = new TextureAtlas(Gdx.files.internal("data/skills/precision.txt"));
		atlas_mine = new TextureAtlas(Gdx.files.internal("data/skills/mine.txt"));
		atlas_fastshot = new TextureAtlas(Gdx.files.internal("data/skills/fastshot.txt"));
		
		//Gunner
		atlas_bulletrain = new TextureAtlas(Gdx.files.internal("data/skills/bulletrain.txt"));
		atlas_lockshot = new TextureAtlas(Gdx.files.internal("data/skills/lockshot.txt"));
		atlas_precision = new TextureAtlas(Gdx.files.internal("data/skills/precision.txt"));
		atlas_mine = new TextureAtlas(Gdx.files.internal("data/skills/mine.txt"));
		atlas_fastshot = new TextureAtlas(Gdx.files.internal("data/skills/fastshot.txt"));
		
		//Thief
		atlas_invisibility = new TextureAtlas(Gdx.files.internal("data/skills/invisibility.txt"));
		atlas_poisonhit = new TextureAtlas(Gdx.files.internal("data/skills/poisonhit.txt"));
		atlas_dashkick = new TextureAtlas(Gdx.files.internal("data/skills/dashkick.txt"));
		atlas_steal = new TextureAtlas(Gdx.files.internal("data/skills/steal.txt"));
		atlas_doublehit = new TextureAtlas(Gdx.files.internal("data/skills/doublehit.txt"));
		
		//Beater
		atlas_hammercrash = new TextureAtlas(Gdx.files.internal("data/skills/hammercrash.txt"));
		atlas_ragebound = new TextureAtlas(Gdx.files.internal("data/skills/ragebound.txt"));
		atlas_overpower = new TextureAtlas(Gdx.files.internal("data/skills/overpower.txt"));
		atlas_berserk = new TextureAtlas(Gdx.files.internal("data/skills/berserk.txt"));
		atlas_impound = new TextureAtlas(Gdx.files.internal("data/skills/impound.txt"));
	}
	
	public Sprite CarregaEfeitoFrame(String nomeSkill, int countFrame) {
		
		//Novice
		if(nomeSkill.equals("tripleattack")) {
			spr_master = atlas_tripleattack.createSprite("tripleattack" + countFrame);
		}
		
		//Mage
		if(nomeSkill.equals("icecrystal")) {
			spr_master = atlas_icecrystal.createSprite("icecrystal" + countFrame);
		}
		if(nomeSkill.equals("fireball")) {
			spr_master = atlas_icecrystal.createSprite("fireball" + countFrame);
		}
		if(nomeSkill.equals("thundercloud")) {
			spr_master = atlas_icecrystal.createSprite("thundercloud" + countFrame);
		}
		if(nomeSkill.equals("rockbound")) {
			spr_master = atlas_icecrystal.createSprite("rockbound" + countFrame);
		}
		if(nomeSkill.equals("soulclash")) {
			spr_master = atlas_icecrystal.createSprite("soulclash" + countFrame);
		}
	
		//Swordman
		if(nomeSkill.equals("flysword")) {
			spr_master = atlas_icecrystal.createSprite("flysword" + countFrame);
		}
		if(nomeSkill.equals("healthboost")) {
			spr_master = atlas_icecrystal.createSprite("healthboost" + countFrame);
		}
		if(nomeSkill.equals("havenblade")) {
			spr_master = atlas_icecrystal.createSprite("havenblade" + countFrame);
		}
		if(nomeSkill.equals("ironshield")) {
			spr_master = atlas_icecrystal.createSprite("ironshield" + countFrame);
			
		}if(nomeSkill.equals("protect")) {
			spr_master = atlas_icecrystal.createSprite("protect" + countFrame);
		}
		
		//Priest
		if(nomeSkill.equals("heal")) {
			spr_master = atlas_icecrystal.createSprite("heal" + countFrame);
		}
		if(nomeSkill.equals("atkboost")) {
			spr_master = atlas_icecrystal.createSprite("atkboost" + countFrame);
		}
		if(nomeSkill.equals("defboost")) {
			spr_master = atlas_icecrystal.createSprite("defboost" + countFrame);
		}
		if(nomeSkill.equals("regen")) {
			spr_master = atlas_icecrystal.createSprite("regen" + countFrame);		
		}
		if(nomeSkill.equals("holyprism")) {
			spr_master = atlas_icecrystal.createSprite("holyprism" + countFrame);
		}
		
		//Gunner
		if(nomeSkill.equals("bulletrain")) {
			spr_master = atlas_icecrystal.createSprite("bulletrain" + countFrame);
		}
		if(nomeSkill.equals("lockshot")) {
			spr_master = atlas_icecrystal.createSprite("lockshot" + countFrame);
		}
		if(nomeSkill.equals("precision")) {
			spr_master = atlas_icecrystal.createSprite("precision" + countFrame);
		}
		if(nomeSkill.equals("mine")) {
			spr_master = atlas_icecrystal.createSprite("mine" + countFrame);		
		}
		if(nomeSkill.equals("fastshot")) {
			spr_master = atlas_icecrystal.createSprite("fastshot" + countFrame);
		}
		
		//Thief
		if(nomeSkill.equals("invisibility")) {
			spr_master = atlas_icecrystal.createSprite("invisibility" + countFrame);
		}
		if(nomeSkill.equals("poisonhit")) {
			spr_master = atlas_icecrystal.createSprite("poisonhit" + countFrame);
		}
		if(nomeSkill.equals("dashkick")) {
			spr_master = atlas_icecrystal.createSprite("dashkick" + countFrame);
		}
		if(nomeSkill.equals("steal")) {
			spr_master = atlas_icecrystal.createSprite("steal" + countFrame);		
		}
		if(nomeSkill.equals("doublehit")) {
			spr_master = atlas_icecrystal.createSprite("doublehit" + countFrame);
		}
		
		//Beater
		if(nomeSkill.equals("hammercrash")) {
			spr_master = atlas_icecrystal.createSprite("hammercrash" + countFrame);
		}
		if(nomeSkill.equals("ragebound")) {
			spr_master = atlas_icecrystal.createSprite("ragebound" + countFrame);
		}
		if(nomeSkill.equals("overpower")) {
			spr_master = atlas_icecrystal.createSprite("overpower" + countFrame);
		}
		if(nomeSkill.equals("berserk")) {
			spr_master = atlas_icecrystal.createSprite("berserk" + countFrame);		
		}
		if(nomeSkill.equals("impound")) {
			spr_master = atlas_icecrystal.createSprite("impound" + countFrame);
		}
		
		//Clown
		if(nomeSkill.equals("drawcard")) {
			spr_master = atlas_icecrystal.createSprite("drawcard" + countFrame);
		}
		if(nomeSkill.equals("spellstep")) {
			spr_master = atlas_icecrystal.createSprite("spellstep" + countFrame);
		}
		if(nomeSkill.equals("creditdance")) {
			spr_master = atlas_icecrystal.createSprite("creditdance" + countFrame);
		}
		if(nomeSkill.equals("malabarism")) {
			spr_master = atlas_icecrystal.createSprite("malabarism" + countFrame);		
		}
		if(nomeSkill.equals("amplitude")) {
			spr_master = atlas_icecrystal.createSprite("amplitude" + countFrame);
		}
		
		return spr_master;
	}
	
	public static Skill RetornaDadosSKill(String nomeSkill, String usr) {
		Skill novaSkill = new Skill();
		
		//Novice
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
		}
		
		//Mage
		if(nomeSkill == "icecrystal") {
			novaSkill.nameSkill = "icecrystal";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 40;
			novaSkill.castTime = 100;
			novaSkill.delay = 30;
			novaSkill.areaSpreadX = 20;
			novaSkill.areaSpreadY = 20;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = true;
		}
		
		if(nomeSkill == "thundercloud") {
			novaSkill.nameSkill = "thundercloud";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 80;
			novaSkill.castTime = 120;
			novaSkill.delay = 20;
			novaSkill.areaSpreadX = 0;
			novaSkill.areaSpreadY = 0;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = true;
		}
		
		if(nomeSkill == "fireball") {
			novaSkill.nameSkill = "fireball";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 40;
			novaSkill.castTime = 30;
			novaSkill.delay = 20;
			novaSkill.areaSpreadX = 30;
			novaSkill.areaSpreadY = 30;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = true;
		}
		
		if(nomeSkill == "rockbound") {
			novaSkill.nameSkill = "rockbound";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 15;
			novaSkill.castTime = 30;
			novaSkill.delay = 10;
			novaSkill.areaSpreadX = 20;
			novaSkill.areaSpreadY = 20;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = true;
		}
		
		if(nomeSkill == "soulclash") {
			novaSkill.nameSkill = "soulclash";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 250;
			novaSkill.castTime = 200;
			novaSkill.delay = 20;
			novaSkill.areaSpreadX = 45;
			novaSkill.areaSpreadY = 45;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = true;
		}
		
		//Doctor
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
			novaSkill.delay = 40;
			novaSkill.areaSpreadX = 30;
			novaSkill.areaSpreadY = 30;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = true;
		}
		if(nomeSkill == "atkboost") {
			novaSkill.nameSkill = "atkboost";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 0;
			novaSkill.castTime = 20;
			novaSkill.delay = 20;
			novaSkill.areaSpreadX = 20;
			novaSkill.areaSpreadY = 20;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = true;
		}
		if(nomeSkill == "defboost") {
			novaSkill.nameSkill = "defboost";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 0;
			novaSkill.castTime = 20;
			novaSkill.delay = 20;
			novaSkill.areaSpreadX = 20;
			novaSkill.areaSpreadY = 20;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = true;
		}
		
		if(nomeSkill == "regen") {
			novaSkill.nameSkill = "regen";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 0;
			novaSkill.castTime = 10;
			novaSkill.delay = 10;
			novaSkill.areaSpreadX = 40;
			novaSkill.areaSpreadY = 40;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = true;
		}
		
		if(nomeSkill == "holyprism") {
			novaSkill.nameSkill = "holyprism";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 100;
			novaSkill.castTime = 30;
			novaSkill.delay = 150;
			novaSkill.areaSpreadX = 40;
			novaSkill.areaSpreadY = 40;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = true;
		}
		
		//Swordman
		
		if(nomeSkill == "flysword") {
			novaSkill.nameSkill = "flysword";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 40;
			novaSkill.castTime = 0;
			novaSkill.delay = 20;
			novaSkill.areaSpreadX = 0;
			novaSkill.areaSpreadY = 0;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = false;
		}
		if(nomeSkill == "healthboost") {
			novaSkill.nameSkill = "healthboost";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 20;
			novaSkill.castTime = 0;
			novaSkill.delay = 200;
			novaSkill.areaSpreadX = 0;
			novaSkill.areaSpreadY = 0;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = false;
		}
		if(nomeSkill == "havenblade") {
			novaSkill.nameSkill = "havenblade";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 70;
			novaSkill.castTime = 0;
			novaSkill.delay = 40;
			novaSkill.areaSpreadX = 0;
			novaSkill.areaSpreadY = 0;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = false;
		}
		if(nomeSkill == "ironshield") {
			novaSkill.nameSkill = "ironshield";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 0;
			novaSkill.castTime = 10;
			novaSkill.delay = 10;
			novaSkill.areaSpreadX = 0;
			novaSkill.areaSpreadY = 0;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = false;
		}
		if(nomeSkill == "protect") {
			novaSkill.nameSkill = "protect";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 0;
			novaSkill.castTime = 50;
			novaSkill.delay = 30;
			novaSkill.areaSpreadX = 40;
			novaSkill.areaSpreadY = 40;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = true;
		}
		
		//Gunner
		if(nomeSkill == "bulletrain") {
			novaSkill.nameSkill = "bulletrain";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 20;
			novaSkill.castTime = 10;
			novaSkill.delay = 10;
			novaSkill.areaSpreadX = 40;
			novaSkill.areaSpreadY = 40;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = true;
		}
		
		if(nomeSkill == "lockshot") {
			novaSkill.nameSkill = "lockshot";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 120;
			novaSkill.castTime = 0;
			novaSkill.delay = 120;
			novaSkill.areaSpreadX = 30;
			novaSkill.areaSpreadY = 30;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = true;
		}
		
		if(nomeSkill == "precision") {
			novaSkill.nameSkill = "precision";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 0;
			novaSkill.castTime = 0;
			novaSkill.delay = 50;
			novaSkill.areaSpreadX = 0;
			novaSkill.areaSpreadY = 0;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = false;
		}
		if(nomeSkill == "mine") {
			novaSkill.nameSkill = "mine";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 50;
			novaSkill.castTime = 0;
			novaSkill.delay = 10;
			novaSkill.areaSpreadX = 1;
			novaSkill.areaSpreadY = 1;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = true;
		}
		if(nomeSkill == "fastshot") {
			novaSkill.nameSkill = "fastshot";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 30;
			novaSkill.castTime = 0;
			novaSkill.delay = 0;
			novaSkill.areaSpreadX = 1;
			novaSkill.areaSpreadY = 1;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = false;
		}
		
		//Beaten
		if(nomeSkill == "hammercrash") {
			novaSkill.nameSkill = "hammercrash";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 120;
			novaSkill.castTime = 0;
			novaSkill.delay = 0;
			novaSkill.areaSpreadX = 0;
			novaSkill.areaSpreadY = 0;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = false;
		}
		if(nomeSkill == "ragebound") {
			novaSkill.nameSkill = "ragebound";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 0;
			novaSkill.castTime = 30;
			novaSkill.delay = 70;
			novaSkill.areaSpreadX = 30;
			novaSkill.areaSpreadY = 30;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = true;
		}
		
		if(nomeSkill == "overpower") {
			novaSkill.nameSkill = "overpower";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 320;
			novaSkill.castTime = 0;
			novaSkill.delay = 220;
			novaSkill.areaSpreadX = 0;
			novaSkill.areaSpreadY = 0;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = false;
		}
		
		if(nomeSkill == "berserk") {
			novaSkill.nameSkill = "berserk";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 0;
			novaSkill.castTime = 0;
			novaSkill.delay = 120;
			novaSkill.areaSpreadX = 0;
			novaSkill.areaSpreadY = 0;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = false;
		}
		if(nomeSkill == "impound") {
			novaSkill.nameSkill = "impound";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 0;
			novaSkill.castTime = 0;
			novaSkill.delay = 70;
			novaSkill.areaSpreadX = 0;
			novaSkill.areaSpreadY = 0;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = false;
		}
		
		//Gambler
		if(nomeSkill == "drawcard") {
			novaSkill.nameSkill = "drawcard";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 0;
			novaSkill.castTime = 0;
			novaSkill.delay = 20;
			novaSkill.areaSpreadX = 0;
			novaSkill.areaSpreadY = 0;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = false;
		}
		
		if(nomeSkill == "spellstep") {
			novaSkill.nameSkill = "spellstep";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 0;
			novaSkill.castTime = 0;
			novaSkill.delay = 20;
			novaSkill.areaSpreadX = 40;
			novaSkill.areaSpreadY = 40;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = true;
		}
		if(nomeSkill == "creditdance") {
			novaSkill.nameSkill = "creditdance";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 0;
			novaSkill.castTime = 0;
			novaSkill.delay = 20;
			novaSkill.areaSpreadX = 0;
			novaSkill.areaSpreadY = 0;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = false;
		}
		if(nomeSkill == "malabarism") {
			novaSkill.nameSkill = "malabarism";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 0;
			novaSkill.castTime = 0;
			novaSkill.delay = 0;
			novaSkill.areaSpreadX = 0;
			novaSkill.areaSpreadY = 0;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = false;
		}
		if(nomeSkill == "amplitude") {
			novaSkill.nameSkill = "amplitude";
			novaSkill.caster = usr;
			novaSkill.posX = 0;
			novaSkill.posY = 0;
			novaSkill.height = 40;
			novaSkill.width = 30;
			novaSkill.timer = 60;
			novaSkill.damage = 80;
			novaSkill.castTime = 0;
			novaSkill.delay = 0;
			novaSkill.areaSpreadX = 0;
			novaSkill.areaSpreadY = 0;
			novaSkill.countFrameEffect = 1;
			novaSkill.overEffect = false;
			novaSkill.isAreaSkill = false;
		}
		
		return novaSkill;
	}
	
	public static boolean CheckMP(String nomeSkill, int MP) {
		if(nomeSkill.equals("tripleattack") && MP < 10) { return false; }		
		return true;
	}
	
	public boolean IsRangedSkill(String nomeSkill) {
		//Novice
		if(nomeSkill.equals("tripleattack")) { return false; }
		
		//Mage
		if(nomeSkill.equals("fireball")) { return true; }
		if(nomeSkill.equals("icecrystal")) { return true; }
		if(nomeSkill.equals("thundercloud")) { return true; }
		if(nomeSkill.equals("rockbound")) { return true; }
		if(nomeSkill.equals("soulclash")) { return true; }
		
		//Doctor
		if(nomeSkill.equals("heal")) { return true; }
		if(nomeSkill.equals("atkboost")) { return true; }
		if(nomeSkill.equals("defboost")) { return true; }
		if(nomeSkill.equals("regen")) { return true; }
		if(nomeSkill.equals("holyprism")) { return false; }
		
		//Thief
		if(nomeSkill.equals("invisibility")) { return false; }
		if(nomeSkill.equals("poisonhit")) { return false; }
		if(nomeSkill.equals("dashkick")) { return false; }
		if(nomeSkill.equals("steal")) { return false; }
		if(nomeSkill.equals("doublehit")) { return false; }
		
		//Swordman
		if(nomeSkill.equals("flysword")) { return false; }
		if(nomeSkill.equals("healthboost")) { return false; }
		if(nomeSkill.equals("havenblade")) { return false; }
		if(nomeSkill.equals("ironshield")) { return false; }
		if(nomeSkill.equals("protect")) { return true; }
		
		//Gunner
		if(nomeSkill.equals("bulletrain")) { return true; }
		if(nomeSkill.equals("lockshot")) { return true; }
		if(nomeSkill.equals("precision")) { return false; }
		if(nomeSkill.equals("mine")) { return true; }
		if(nomeSkill.equals("fastshot")) { return false; }
		
		//Beater
		if(nomeSkill.equals("hammercrash")) { return false; }
		if(nomeSkill.equals("ragebound")) { return false; }
		if(nomeSkill.equals("overpower")) { return false; }
		if(nomeSkill.equals("berserk")) { return false; }
		if(nomeSkill.equals("impound")) { return true; }
		
		//Gambler
		if(nomeSkill.equals("drawcard")) { return false; }
		if(nomeSkill.equals("spellstep")) { return true; }
		if(nomeSkill.equals("creditdance")) { return false; }
		if(nomeSkill.equals("malabarism")) { return false; }
		if(nomeSkill.equals("amplitude")) { return false; }
		
		
		return false;
	}
	
	public int CalculaDanoSkill(Skill sk, Player char_data){
		
		int pMind;
		int pDex;
		int pStr;
		int pAgi;
		int pluk;
		int dmg = 0;
		
		pStr = Integer.parseInt(char_data.Strengh_A);
		pMind = Integer.parseInt(char_data.Mind_A);
		
		////////Novice //////
		if(sk.nameSkill.equals("tripleattack")){
			dmg = pStr * 3;
		}
		
		////////Mage //////
		if(sk.nameSkill.equals("fireball")){
			dmg = (pMind + 20) * 3;
		}
		
		if(sk.nameSkill.equals("thundercloud")){
			dmg = (pMind + 20) * 3;
		}
		
		if(sk.nameSkill.equals("rockbound")){
			dmg = (pMind + 20) * 3;
		}
		
		if(sk.nameSkill.equals("soulclash")){
			dmg = (pMind + 20) * 3;
		}
		
		if(sk.nameSkill.equals("icecrystal")){
			dmg = (pMind + 20) * 3;
		}
		
		////////Swordman //////
		if(sk.nameSkill.equals("flysword")){
			dmg = (pMind + 20) * 3;
		}
		
		if(sk.nameSkill.equals("healthboost")){
			dmg = (pMind + 20) * 3;
		}
		
		if(sk.nameSkill.equals("havenblade")){
			dmg = (pMind + 20) * 3;
		}
		
		if(sk.nameSkill.equals("ironshield")){
			dmg = (pMind + 20) * 3;
		}
		
		if(sk.nameSkill.equals("protect")){
			dmg = (pMind + 20) * 3;
		}
		
		return dmg;
	}
}


