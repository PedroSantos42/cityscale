package com.moonbolt.cityscale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class GameControl {
		
		//SUMMARY
		//[Data Process]//
		//Character Movement and Objects //
		// Scenario Objects //
		// Interfaces and Screens //
		// Monsters //
		// Battle Settings //
		// Itens Management //
		// Buffs and Debuffs //
		// NPCS and Quests //
	
		//Global Variables 
		private Json json;
		private FileHandle file;
		private Random randnumber;
		
		private String text;
		private String weapon;
		private String[] charData;
		private String[] itemUsage;
	    private String qtdItem;
	    private String textQuest = "";
		
		private boolean inBattle = false;
		private boolean attackFrame = false;
		
		private int countA;
		private int countB;
		private int regenHPSP = 500;
		private int dmgWeapon;
		private int frameMove;
		private int pos;
		private int attackCooldown = 0;
		private int charNumActive;
		private int playerHP;
		private int playerHPMAX;
		private int playerMP;
		private int playerMPMAX;
		private int playerAtk;
		private int playerDef;
		private int playerStrenght;
		private int playerAgility;
		private int playerLucky;
		private int playerDextery;
		private int playerMind;
		private int playerAttackCooldown = 120;
		private int mobHP;
		private int mobAtk;
		private int mobDef;
		private int monsterEvade;
		private int mobAttackCooldown = 0;
		private int playerbattleframe = 0;
		private int playerManualAtkDelay = 40;
		
	    private float npcframe = 1;
	    private float npcframe2 = 2;
	    private float npcframewalk = 1;
	    private float endright = 0;
	    private float endleft = 0;
		
		private float fX;
		private float fY;
		private float fUsable;
		private float mobX;
		private float mobY;
		private float mobAttackZoneXPlus;
		private float mobAttackZoneXMinus;
		private float mobAttackZoneYPlus;
		private float mobAttackZoneYMinus;
		private float pX;
		private float pY;
		private float pAttackZoneXPlus;
		private float pAttackZoneXMinus;
		private float pAttackZoneYPlus;
		private float pAttackZoneYMinus;
		
		private Player Character_Data;
		private Monster mobContainer;
		private Damage Dmg;
		
		private ArrayList<Monster> lstMonsters;
		private ArrayList<Damage> lstDamage;
		private ArrayList<Player> lstOnlinePlayers;
		private ArrayList<Skills> lstSkills;
		private ArrayList<Buffs> lstBuffs;
		private ArrayList<Sprite> lstSprites;
		private ArrayList<String> lstNomes;
		
		private TextureAtlas atlas_hairs;
		private TextureAtlas atlas_basic_male_set;
		private TextureAtlas atlas_basic_female_set;
		private TextureAtlas atlas_gameplay_interface;
		private TextureAtlas atlas_shop;
		private TextureAtlas atlas_objectsMetro;
		private TextureAtlas atlas_Mob;
		private TextureAtlas atlas_MonsterSlime;
		private TextureAtlas atlas_Usable;
		private TextureAtlas atlas_Loots;
		private TextureAtlas atlas_Npcs;
		private TextureAtlas atlas_nKnifes;
		private TextureAtlas atlas_sets;
		private Sprite spr_master;
		private Texture tex_teste;
		
		//Constructor//
		public GameControl(){
			charNumActive = 0;
			frameMove = 1;
			charData = new String[50];
			itemUsage = new String[2];
			json = new Json();
			
			tex_teste = new Texture(Gdx.files.internal("data/assets/testdot.png"));
			spr_master = new Sprite(tex_teste);
			mobContainer = new Monster();
			
			//Instances of lists//
			lstMonsters = new ArrayList<Monster>();
			lstDamage = new ArrayList<Damage>();
			lstOnlinePlayers = new ArrayList<Player>();
			lstSkills = new ArrayList<Skills>();
			lstSprites = new ArrayList<Sprite>();
			lstNomes = new ArrayList<String>();
			lstBuffs = new ArrayList<Buffs>();
			
			////////Atlas Section//////
			//Character
			atlas_hairs = new TextureAtlas(Gdx.files.internal("data/characters/hair/hairs.txt"));
			atlas_basic_male_set = new TextureAtlas(Gdx.files.internal("data/characters/basic_male/basic_set_male.txt"));
			atlas_basic_female_set = new TextureAtlas(Gdx.files.internal("data/characters/basic_female/basic_female.txt"));
			
			//Gameplay
			atlas_gameplay_interface = new TextureAtlas(Gdx.files.internal("data/interface/gameplay/gameplay.txt"));
			atlas_objectsMetro = new TextureAtlas(Gdx.files.internal("data/assets/objects1.txt"));
			atlas_shop = new TextureAtlas(Gdx.files.internal("data/interface/shops/shops.txt"));
			
			//Monsters
			atlas_MonsterSlime = new TextureAtlas(Gdx.files.internal("data/monsters/mobs.txt"));
			
			//Itens
			atlas_Usable = new TextureAtlas(Gdx.files.internal("data/itens/Usables/Usables.txt"));		
			atlas_Loots = new TextureAtlas(Gdx.files.internal("data/itens/Loots/Loots.txt"));
			
			//NPCs
			atlas_Npcs = new TextureAtlas(Gdx.files.internal("data/characters/npcs/npcs.txt"));
			
			//Armas
			atlas_nKnifes = new TextureAtlas(Gdx.files.internal("data/itens/weapons/nknifes.txt"));
			
			//Equips
			atlas_sets = new TextureAtlas(Gdx.files.internal("data/itens/Sets/sets.txt"));
			////////Atlas Section//////
		}
		
		//[Data Process]//
		public void CreateNewData(){			
			countA = 0;	   	    
			randnumber = new Random();
			FileHandle file = Gdx.files.local("SaveData/SvDT.json");
			while(countA <= 100000){
				countA = randnumber.nextInt(999999);
			}
					
			if (!file.exists()) {
				Character_Data = new Player();
				Character_Data.setAccount(String.valueOf(countA));
				Character_Data.setSituation("Valid");
				
				Character_Data.setName_1("none");
				Character_Data.setName_2("none");
				Character_Data.setName_3("none");
				
				file.writeString(Base64Coder.encodeString(json.prettyPrint(Character_Data)),false);
			}			
		}
		
		public void LoadData() {
			FileHandle file = Gdx.files.local("SaveData/SvDT.json");
			Character_Data = json.fromJson(Player.class,Base64Coder.decodeString(file.readString()));
		}
		
		public void CreateNewCharacter(String[] configChar) {
			String name = configChar[0];
			String hair = configChar[1];
			String sex = configChar[2];
			String lstItens = "";
			int charNumber = 0;
			boolean checkCreated = false;
			
			FileHandle file = Gdx.files.local("SaveData/SvDT.json");
			LoadData();
			
			if(Character_Data.Name_1.equals("none") && !checkCreated){ charNumber = 1; checkCreated = true; }
			if(Character_Data.Name_2.equals("none") && !checkCreated){ charNumber = 2; checkCreated = true; }
			if(Character_Data.Name_3.equals("none") && !checkCreated){ charNumber = 3; checkCreated = true; }
			
			//GeraItens
			for(int i = 1; i < 61; i++) {
				if(i == 60) { 
				lstItens = lstItens + "[None]";
				}
				else {
				lstItens = lstItens + "[None]-";
				}
			}
			
			if(charNumber == 1) {
				Character_Data.setName_1(name);
				Character_Data.setJob_1("Novice");
				Character_Data.setLevel_1("1");
				Character_Data.setSex_1(sex);
				Character_Data.setAtk_1("2");
				Character_Data.setDef_1("2");
				Character_Data.setHP_1("100");
				Character_Data.setMP_1("100");
				Character_Data.setHPMAX_1("100");
				Character_Data.setMPMAX_1("100");
				Character_Data.setStatusPoint_1("0"); 
				Character_Data.setSkillPoint_1("0");
				Character_Data.setExp_1("0");
				Character_Data.setPX_1("11");
				Character_Data.setPY_1("12"); 
				Character_Data.setJump_1("off"); 
				Character_Data.setCouch_1("off"); 
				Character_Data.setMoney_1("0"); 
				Character_Data.setStamina_1("100");
				Character_Data.setStarve_1("no");
				Character_Data.setSpeed_1("250"); 
				Character_Data.setState_1("Front"); 
				Character_Data.setWalk_1("Stop"); 
				Character_Data.setBattle_1("off");
				Character_Data.setTarget_1("none");
				Character_Data.setParty_1("none");
				Character_Data.setMap_1("MetroStation");
				
				if(sex.equals("M")) {
				Character_Data.setWeapon_1("basic_knife"); 
				Character_Data.setHat_1("none"); 
				Character_Data.setHair_1(hair);
				Character_Data.setSet_1("basic_set_male"); 
				Character_Data.setAcessoryA_1("none");
				Character_Data.setAcessoryB_1("none");
				}
				else {
				Character_Data.setWeapon_1("basic_knife"); 
				Character_Data.setHat_1("none"); 
				Character_Data.setHair_1(hair);
				Character_Data.setSet_1("basic_set_female"); 
				Character_Data.setAcessoryA_1("none");
				Character_Data.setAcessoryB_1("none");
				}
				
				Character_Data.setStrengh_1("1");
				Character_Data.setAgility_1("1"); 
				Character_Data.setResistence_1("1"); 
				Character_Data.setVitality_1("1"); 
				Character_Data.setDextery_1("1");
				Character_Data.setMind_1("1"); 
				Character_Data.setLucky_1("1");
				
				Character_Data.setStatus_1("");
				Character_Data.setSkills_1("");
				Character_Data.setItens_1(lstItens); //-[Item#Qtd]
				Character_Data.setCrystals_1("");
				Character_Data.setQuests_1("");
				Character_Data.setActiveQuest_1("");
				Character_Data.setPet_1("");
				Character_Data.setOnline_1("");				
			}
			if(charNumber == 2) {
				Character_Data.setName_2(name);
				Character_Data.setJob_2("Novice");
				Character_Data.setLevel_2("1");
				Character_Data.setSex_2(sex);
				Character_Data.setAtk_2("2");
				Character_Data.setDef_2("2");
				Character_Data.setHP_2("100");
				Character_Data.setMP_2("100");
				Character_Data.setHPMAX_2("100");
				Character_Data.setMPMAX_2("100");
				Character_Data.setStatusPoint_2("0"); 
				Character_Data.setSkillPoint_2("0");
				Character_Data.setExp_2("0");
				Character_Data.setPX_2("11");
				Character_Data.setPY_2("12"); 
				Character_Data.setJump_2("off"); 
				Character_Data.setCouch_2("off"); 
				Character_Data.setMoney_2("0"); 
				Character_Data.setStamina_2("100");
				Character_Data.setStarve_2("no");
				Character_Data.setSpeed_2("250"); 
				Character_Data.setState_2("Front"); 
				Character_Data.setWalk_2("Stop");
				Character_Data.setBattle_2("off");
				Character_Data.setTarget_2("none");
				Character_Data.setParty_2("none");
				Character_Data.setMap_2("MetroStation");
				
				if(sex.equals("M")) {
				Character_Data.setWeapon_2("basic_knife"); 
				Character_Data.setHat_2("none"); 
				Character_Data.setHair_2(hair);
				Character_Data.setSet_2("basic_set_male"); 
				Character_Data.setAcessoryA_2("none");
				Character_Data.setAcessoryB_2("none");
				}
				else {
				Character_Data.setWeapon_2("basic_knife"); 
				Character_Data.setHat_2("none"); 
				Character_Data.setHair_2(hair);
				Character_Data.setSet_2("basic_set_female"); 
				Character_Data.setAcessoryA_2("none");
				Character_Data.setAcessoryB_2("none");
				}
				
				Character_Data.setStrengh_2("1");
				Character_Data.setAgility_2("1"); 
				Character_Data.setResistence_2("1"); 
				Character_Data.setVitality_2("1"); 
				Character_Data.setDextery_2("1");
				Character_Data.setMind_2("1"); 
				Character_Data.setLucky_2("1");
				
				Character_Data.setStatus_2("");
				Character_Data.setSkills_2("");
				Character_Data.setItens_2(lstItens);
				Character_Data.setCrystals_2("");
				Character_Data.setQuests_2("");
				Character_Data.setActiveQuest_2("");
				Character_Data.setPet_2("");
				Character_Data.setOnline_2("");				
			}
			if(charNumber == 3) {
				Character_Data.setName_3(name);
				Character_Data.setJob_3("Novice");
				Character_Data.setLevel_3("1");
				Character_Data.setSex_3(sex);
				Character_Data.setAtk_3("2");
				Character_Data.setDef_3("2");
				Character_Data.setHP_3("100");
				Character_Data.setMP_3("100");
				Character_Data.setHPMAX_3("100");
				Character_Data.setMPMAX_3("100");
				Character_Data.setStatusPoint_3("0"); 
				Character_Data.setSkillPoint_3("0");
				Character_Data.setExp_3("0");
				Character_Data.setPX_3("11");
				Character_Data.setPY_3("12"); 
				Character_Data.setJump_3("off"); 
				Character_Data.setCouch_3("off"); 
				Character_Data.setMoney_3("0"); 
				Character_Data.setStamina_3("100");
				Character_Data.setStarve_3("no");
				Character_Data.setSpeed_3("250"); 
				Character_Data.setState_3("Front");
				Character_Data.setWalk_3("Stop");
				Character_Data.setBattle_3("off");
				Character_Data.setTarget_3("none");
				Character_Data.setParty_3("none");
				Character_Data.setMap_3("MetroStation");
				
				if(sex.equals("M")) {
				Character_Data.setWeapon_3("basic_knife"); 
				Character_Data.setHat_3("none"); 
				Character_Data.setHair_3(hair);
				Character_Data.setSet_3("basic_set_male"); 
				Character_Data.setAcessoryA_3("none");
				Character_Data.setAcessoryB_3("none");
				}
				else {
				Character_Data.setWeapon_3("basic_knife"); 
				Character_Data.setHat_3("none"); 
				Character_Data.setHair_3(hair);
				Character_Data.setSet_3("basic_set_female"); 
				Character_Data.setAcessoryA_3("none");
				Character_Data.setAcessoryB_3("none");
				}
				
				Character_Data.setStrengh_3("1");
				Character_Data.setAgility_3("1"); 
				Character_Data.setResistence_3("1"); 
				Character_Data.setVitality_3("1"); 
				Character_Data.setDextery_3("1");
				Character_Data.setMind_3("1"); 
				Character_Data.setLucky_3("1");
				
				Character_Data.setStatus_3("");
				Character_Data.setSkills_3("");
				Character_Data.setItens_3(lstItens);
				Character_Data.setCrystals_3("");
				Character_Data.setQuests_3("");
				Character_Data.setActiveQuest_3("");
				Character_Data.setPet_3("");
				Character_Data.setOnline_3("");				
			}
			
			file.writeString(Base64Coder.encodeString(json.prettyPrint(Character_Data)),false);
		}
		
		public void AtualizaMapa(String NextMap) {
			
			if(NextMap.equals("Streets305")) {
			Character_Data.Map_A = "Streets305";
			Character_Data.PX_A = "12";
			Character_Data.PY_A = "66";
			}
			if(NextMap.equals("MetroStation")) {
			Character_Data.Map_A = "MetroStation";
			Character_Data.PX_A = "12";
			Character_Data.PY_A = "11";
			}
		}
		
		public void SaveData() {		
			FileHandle file = Gdx.files.local("SaveData/SvDT.json");
			file.writeString(Base64Coder.encodeString(json.prettyPrint(Character_Data)),false);
		}
		
		public void WriteDataCharacterActive() {
			if(charNumActive == 1) {
				Character_Data.Name_1 = Character_Data.Name_A;
				Character_Data.Job_1 = Character_Data.Job_A;
				Character_Data.Level_1 = Character_Data.Level_A;
				Character_Data.Sex_1 = Character_Data.Sex_A;
				Character_Data.Atk_1 = Character_Data.Atk_A;
				Character_Data.Def_1 = Character_Data.Def_A;
				Character_Data.HP_1 = Character_Data.HP_A;
				Character_Data.MP_1 = Character_Data.MP_A;
				Character_Data.HPMAX_1 = Character_Data.HPMAX_A;
				Character_Data.MPMAX_1 = Character_Data.MPMAX_A;
				Character_Data.StatusPoint_1 = Character_Data.StatusPoint_A;
				Character_Data.SkillPoint_1 = Character_Data.SkillPoint_A;
				Character_Data.Exp_1 = Character_Data.Exp_A;
				Character_Data.PX_1 = Character_Data.PX_A;
				Character_Data.PY_1 = Character_Data.PY_A;
				Character_Data.Jump_1 = Character_Data.Jump_A;
				Character_Data.Couch_1 = Character_Data.Couch_A;
				Character_Data.Money_1 = Character_Data.Money_A;
				Character_Data.Stamina_1 = Character_Data.Stamina_A;
				Character_Data.Starve_1 = Character_Data.Starve_A;
				Character_Data.Speed_1 = Character_Data.Speed_A;
				Character_Data.State_1 = Character_Data.State_A;
				Character_Data.Walk_1 = Character_Data.Walk_A;
				Character_Data.Battle_1 = Character_Data.Battle_A;
				Character_Data.Target_1 = Character_Data.Target_A;
				Character_Data.Party_1 = Character_Data.Party_A;
				Character_Data.Map_1 = Character_Data.Map_A;
				
				Character_Data.Weapon_1 = Character_Data.Weapon_A;
				Character_Data.Hat_1 = Character_Data.Hat_A;
				Character_Data.Hair_1 = Character_Data.Hair_A;
				Character_Data.Set_1 = Character_Data.Set_A;
				Character_Data.AcessoryA_1 = Character_Data.AcessoryA_A;
				Character_Data.AcessoryB_1 = Character_Data.AcessoryB_A;
				
				Character_Data.Strengh_1 = Character_Data.Strengh_A;
				Character_Data.Agility_1 = Character_Data.Agility_A;
				Character_Data.Resistence_1 = Character_Data.Resistence_A;
				Character_Data.Vitality_1 = Character_Data.Vitality_A;
				Character_Data.Dextery_1 = Character_Data.Dextery_A;
				Character_Data.Mind_1 = Character_Data.Mind_A;
				Character_Data.Lucky_1 = Character_Data.Lucky_A;
				
				Character_Data.Status_1 = Character_Data.Status_A;
				Character_Data.Skills_1 = Character_Data.Skills_A;
				Character_Data.Itens_1 = Character_Data.Itens_A;
				Character_Data.Crystals_1 = Character_Data.Crystals_A;
				Character_Data.Quests_1 = Character_Data.Quests_A;
				Character_Data.ActiveQuest_1 = Character_Data.ActiveQuest_A;
				Character_Data.Pet_1 = Character_Data.Pet_A;
				Character_Data.Online_1 = Character_Data.Online_A;
			}
			if(charNumActive == 2) {
				Character_Data.Name_2 = Character_Data.Name_A;
				Character_Data.Job_2 = Character_Data.Job_A;
				Character_Data.Level_2 = Character_Data.Level_A;
				Character_Data.Sex_2 = Character_Data.Sex_A;
				Character_Data.Atk_2 = Character_Data.Atk_A;
				Character_Data.Def_2 = Character_Data.Def_A;
				Character_Data.HP_2 = Character_Data.HP_A;
				Character_Data.MP_2 = Character_Data.MP_A;
				Character_Data.HPMAX_2 = Character_Data.HPMAX_A;
				Character_Data.MPMAX_2 = Character_Data.MPMAX_A;
				Character_Data.StatusPoint_2 = Character_Data.StatusPoint_A;
				Character_Data.SkillPoint_2 = Character_Data.SkillPoint_A;
				Character_Data.Exp_2 = Character_Data.Exp_A;
				Character_Data.PX_2 = Character_Data.PX_A;
				Character_Data.PY_2 = Character_Data.PY_A;
				Character_Data.Jump_2 = Character_Data.Jump_A;
				Character_Data.Couch_2 = Character_Data.Couch_A;
				Character_Data.Money_2 = Character_Data.Money_A;
				Character_Data.Stamina_2 = Character_Data.Stamina_A;
				Character_Data.Starve_2 = Character_Data.Starve_A;
				Character_Data.Speed_2 = Character_Data.Speed_A;
				Character_Data.State_2 = Character_Data.State_A;
				Character_Data.Walk_2 = Character_Data.Walk_A;
				Character_Data.Battle_2 = Character_Data.Battle_A;
				Character_Data.Target_2 = Character_Data.Target_A;
				Character_Data.Party_2 = Character_Data.Party_A;
				Character_Data.Map_2 = Character_Data.Map_A;
				
				Character_Data.Weapon_2 = Character_Data.Weapon_A;
				Character_Data.Hat_2 = Character_Data.Hat_A;
				Character_Data.Hair_2 = Character_Data.Hair_A;
				Character_Data.Set_2 = Character_Data.Set_A;
				Character_Data.AcessoryA_2 = Character_Data.AcessoryA_A;
				Character_Data.AcessoryB_2 = Character_Data.AcessoryB_A;
				
				Character_Data.Strengh_2 = Character_Data.Strengh_A;
				Character_Data.Agility_2 = Character_Data.Agility_A;
				Character_Data.Resistence_2 = Character_Data.Resistence_A;
				Character_Data.Vitality_2 = Character_Data.Vitality_A;
				Character_Data.Dextery_2 = Character_Data.Dextery_A;
				Character_Data.Mind_2 = Character_Data.Mind_A;
				Character_Data.Lucky_2 = Character_Data.Lucky_A;
				
				Character_Data.Status_2 = Character_Data.Status_A;
				Character_Data.Skills_2 = Character_Data.Skills_A;
				Character_Data.Itens_2 = Character_Data.Itens_A;
				Character_Data.Crystals_2 = Character_Data.Crystals_A;
				Character_Data.Quests_2 = Character_Data.Quests_A;
				Character_Data.ActiveQuest_2 = Character_Data.ActiveQuest_A;
				Character_Data.Pet_2 = Character_Data.Pet_A;
				Character_Data.Online_2 = Character_Data.Online_A;
			}
			if(charNumActive == 3) {
				Character_Data.Name_3 = Character_Data.Name_A;
				Character_Data.Job_3 = Character_Data.Job_A;
				Character_Data.Level_3 = Character_Data.Level_A;
				Character_Data.Sex_3 = Character_Data.Sex_A;
				Character_Data.Atk_3 = Character_Data.Atk_A;
				Character_Data.Def_3 = Character_Data.Def_A;
				Character_Data.HP_3 = Character_Data.HP_A;
				Character_Data.MP_3 = Character_Data.MP_A;
				Character_Data.HPMAX_3 = Character_Data.HPMAX_A;
				Character_Data.MPMAX_3 = Character_Data.MPMAX_A;
				Character_Data.StatusPoint_3 = Character_Data.StatusPoint_A;
				Character_Data.SkillPoint_3 = Character_Data.SkillPoint_A;
				Character_Data.Exp_3 = Character_Data.Exp_A;
				Character_Data.PX_3 = Character_Data.PX_A;
				Character_Data.PY_3 = Character_Data.PY_A;
				Character_Data.Jump_3 = Character_Data.Jump_A;
				Character_Data.Couch_3 = Character_Data.Couch_A;
				Character_Data.Money_3 = Character_Data.Money_A;
				Character_Data.Stamina_3 = Character_Data.Stamina_A;
				Character_Data.Starve_3 = Character_Data.Starve_A;
				Character_Data.Speed_3 = Character_Data.Speed_A;
				Character_Data.State_3 = Character_Data.State_A;
				Character_Data.Walk_3 = Character_Data.Walk_A;
				Character_Data.Battle_3 = Character_Data.Battle_A;
				Character_Data.Target_3 = Character_Data.Target_A;
				Character_Data.Party_3 = Character_Data.Party_A;
				Character_Data.Map_3 = Character_Data.Map_A;
				
				Character_Data.Weapon_3 = Character_Data.Weapon_A;
				Character_Data.Hat_3 = Character_Data.Hat_A;
				Character_Data.Hair_3 = Character_Data.Hair_A;
				Character_Data.Set_3 = Character_Data.Set_A;
				Character_Data.AcessoryA_3 = Character_Data.AcessoryA_A;
				Character_Data.AcessoryB_3 = Character_Data.AcessoryB_A;
				
				Character_Data.Strengh_3 = Character_Data.Strengh_A;
				Character_Data.Agility_3 = Character_Data.Agility_A;
				Character_Data.Resistence_3 = Character_Data.Resistence_A;
				Character_Data.Vitality_3 = Character_Data.Vitality_A;
				Character_Data.Dextery_3 = Character_Data.Dextery_A;
				Character_Data.Mind_3 = Character_Data.Mind_A;
				Character_Data.Lucky_3 = Character_Data.Lucky_A;
				
				Character_Data.Status_3 = Character_Data.Status_A;
				Character_Data.Skills_3 = Character_Data.Skills_A;
				Character_Data.Itens_3 = Character_Data.Itens_A;
				Character_Data.Crystals_3 = Character_Data.Crystals_A;
				Character_Data.Quests_3 = Character_Data.Quests_A;
				Character_Data.ActiveQuest_3 = Character_Data.ActiveQuest_A;
				Character_Data.Pet_3 = Character_Data.Pet_A;
				Character_Data.Online_3 = Character_Data.Online_A;
			}
		}
		
		public void SetNumberChar(int charnum) {
			 charNumActive = charnum;
		}
		
		public int RecoverActiveChar() {
			return charNumActive;
		}
		
		public Player AtualizaDadosPlayer() {
			return Character_Data;
		}
		
		public Player SetActiveCharacter(int charnum) {
			if(charnum == 1) {
				Character_Data.Name_A = Character_Data.Name_1;
				Character_Data.Job_A = Character_Data.Job_1;
				Character_Data.Level_A = Character_Data.Level_1;
				Character_Data.Sex_A = Character_Data.Sex_1;
				Character_Data.Atk_A = Character_Data.Atk_1;
				Character_Data.Def_A = Character_Data.Def_1;
				Character_Data.HP_A = Character_Data.HP_1;
				Character_Data.MP_A = Character_Data.MP_1;
				Character_Data.HPMAX_A = Character_Data.HPMAX_1;
				Character_Data.MPMAX_A = Character_Data.MPMAX_1;
				Character_Data.StatusPoint_A = Character_Data.StatusPoint_1;
				Character_Data.SkillPoint_A = Character_Data.SkillPoint_1;
				Character_Data.Exp_A = Character_Data.Exp_1;
				Character_Data.PX_A = Character_Data.PX_1;
				Character_Data.PY_A = Character_Data.PY_1;
				Character_Data.Jump_A = Character_Data.Jump_1;
				Character_Data.Couch_A = Character_Data.Couch_1;
				Character_Data.Money_A = Character_Data.Money_1;
				Character_Data.Stamina_A = Character_Data.Stamina_1;
				Character_Data.Starve_A = Character_Data.Starve_1;
				Character_Data.Speed_A = Character_Data.Speed_1;
				Character_Data.State_A = Character_Data.State_1;
				Character_Data.Walk_A = Character_Data.Walk_1;
				Character_Data.Battle_A = Character_Data.Battle_1;
				Character_Data.Target_A = Character_Data.Target_1;
				Character_Data.Party_A = Character_Data.Party_1;
				Character_Data.Map_A = Character_Data.Map_1;
				
				Character_Data.Weapon_A = Character_Data.Weapon_1;
				Character_Data.Hat_A = Character_Data.Hat_1;
				Character_Data.Hair_A = Character_Data.Hair_1;
				Character_Data.Set_A = Character_Data.Set_1;
				Character_Data.AcessoryA_A = Character_Data.AcessoryA_1;
				Character_Data.AcessoryB_A = Character_Data.AcessoryB_1;
				
				Character_Data.Strengh_A = Character_Data.Strengh_1;
				Character_Data.Agility_A = Character_Data.Agility_1;
				Character_Data.Resistence_A = Character_Data.Resistence_1;
				Character_Data.Vitality_A = Character_Data.Vitality_1;
				Character_Data.Dextery_A = Character_Data.Dextery_1;
				Character_Data.Mind_A = Character_Data.Mind_1;
				Character_Data.Lucky_A = Character_Data.Lucky_1;
				
				Character_Data.Status_A = Character_Data.Status_1;
				Character_Data.Skills_A = Character_Data.Skills_1;
				Character_Data.Itens_A = Character_Data.Itens_1;
				Character_Data.Crystals_A = Character_Data.Crystals_1;
				Character_Data.Quests_A = Character_Data.Quests_1;
				Character_Data.ActiveQuest_A = Character_Data.ActiveQuest_1;
				Character_Data.Pet_A = Character_Data.Pet_1;
				Character_Data.Online_A = Character_Data.Online_1;
			}
			if(charnum == 2) {
				Character_Data.Name_A = Character_Data.Name_2;
				Character_Data.Job_A = Character_Data.Job_2;
				Character_Data.Level_A = Character_Data.Level_2;
				Character_Data.Sex_A = Character_Data.Sex_2;
				Character_Data.Atk_A = Character_Data.Atk_2;
				Character_Data.Def_A = Character_Data.Def_2;
				Character_Data.HP_A = Character_Data.HP_2;
				Character_Data.MP_A = Character_Data.MP_2;
				Character_Data.HPMAX_A = Character_Data.HPMAX_2;
				Character_Data.MPMAX_A = Character_Data.MPMAX_2;
				Character_Data.StatusPoint_A = Character_Data.StatusPoint_2;
				Character_Data.SkillPoint_A = Character_Data.SkillPoint_2;
				Character_Data.Exp_A = Character_Data.Exp_2;
				Character_Data.PX_A = Character_Data.PX_2;
				Character_Data.PY_A = Character_Data.PY_2;
				Character_Data.Jump_A = Character_Data.Jump_2;
				Character_Data.Couch_A = Character_Data.Couch_2;
				Character_Data.Money_A = Character_Data.Money_2;
				Character_Data.Stamina_A = Character_Data.Stamina_2;
				Character_Data.Starve_A = Character_Data.Starve_2;
				Character_Data.Speed_A = Character_Data.Speed_2;
				Character_Data.State_A = Character_Data.State_2;
				Character_Data.Walk_A = Character_Data.Walk_2;
				Character_Data.Battle_A = Character_Data.Battle_2;
				Character_Data.Target_A = Character_Data.Target_2;
				Character_Data.Party_A = Character_Data.Party_2;
				Character_Data.Map_A = Character_Data.Map_2;
				
				Character_Data.Weapon_A = Character_Data.Weapon_2;
				Character_Data.Hat_A = Character_Data.Hat_2;
				Character_Data.Hair_A = Character_Data.Hair_2;
				Character_Data.Set_A = Character_Data.Set_2;
				Character_Data.AcessoryA_A = Character_Data.AcessoryA_2;
				Character_Data.AcessoryB_A = Character_Data.AcessoryB_2;
				
				Character_Data.Strengh_A = Character_Data.Strengh_2;
				Character_Data.Agility_A = Character_Data.Agility_2;
				Character_Data.Resistence_A = Character_Data.Resistence_2;
				Character_Data.Vitality_A = Character_Data.Vitality_2;
				Character_Data.Dextery_A = Character_Data.Dextery_2;
				Character_Data.Mind_A = Character_Data.Mind_2;
				Character_Data.Lucky_A = Character_Data.Lucky_2;
				
				Character_Data.Status_A = Character_Data.Status_2;
				Character_Data.Skills_A = Character_Data.Skills_2;
				Character_Data.Itens_A = Character_Data.Itens_2;
				Character_Data.Crystals_A = Character_Data.Crystals_2;
				Character_Data.Quests_A = Character_Data.Quests_2;
				Character_Data.ActiveQuest_A = Character_Data.ActiveQuest_2;
				Character_Data.Pet_A = Character_Data.Pet_2;
				Character_Data.Online_A = Character_Data.Online_2;
			}
			if(charnum == 3) {
				Character_Data.Name_A = Character_Data.Name_3;
				Character_Data.Job_A = Character_Data.Job_3;
				Character_Data.Level_A = Character_Data.Level_3;
				Character_Data.Sex_A = Character_Data.Sex_3;
				Character_Data.Atk_A = Character_Data.Atk_3;
				Character_Data.Def_A = Character_Data.Def_3;
				Character_Data.HP_A = Character_Data.HP_3;
				Character_Data.MP_A = Character_Data.MP_3;
				Character_Data.HPMAX_A = Character_Data.HPMAX_3;
				Character_Data.MPMAX_A = Character_Data.MPMAX_3;
				Character_Data.StatusPoint_A = Character_Data.StatusPoint_3;
				Character_Data.SkillPoint_A = Character_Data.SkillPoint_3;
				Character_Data.Exp_A = Character_Data.Exp_3;
				Character_Data.PX_A = Character_Data.PX_3;
				Character_Data.PY_A = Character_Data.PY_3;
				Character_Data.Jump_A = Character_Data.Jump_3;
				Character_Data.Couch_A = Character_Data.Couch_3;
				Character_Data.Money_A = Character_Data.Money_3;
				Character_Data.Stamina_A = Character_Data.Stamina_3;
				Character_Data.Starve_A = Character_Data.Starve_3;
				Character_Data.Speed_A = Character_Data.Speed_3;
				Character_Data.State_A = Character_Data.State_3;
				Character_Data.Walk_A = Character_Data.Walk_3;
				Character_Data.Battle_A = Character_Data.Battle_3;
				Character_Data.Target_A = Character_Data.Target_3;
				Character_Data.Party_A = Character_Data.Party_3;
				Character_Data.Map_A = Character_Data.Map_3;
				
				Character_Data.Weapon_A = Character_Data.Weapon_3;
				Character_Data.Hat_A = Character_Data.Hat_3;
				Character_Data.Hair_A = Character_Data.Hair_3;
				Character_Data.Set_A = Character_Data.Set_3;
				Character_Data.AcessoryA_A = Character_Data.AcessoryA_3;
				Character_Data.AcessoryB_A = Character_Data.AcessoryB_3;
				
				Character_Data.Strengh_A = Character_Data.Strengh_3;
				Character_Data.Agility_A = Character_Data.Agility_3;
				Character_Data.Resistence_A = Character_Data.Resistence_3;
				Character_Data.Vitality_A = Character_Data.Vitality_3;
				Character_Data.Dextery_A = Character_Data.Dextery_3;
				Character_Data.Mind_A = Character_Data.Mind_3;
				Character_Data.Lucky_A = Character_Data.Lucky_3;
				
				Character_Data.Status_A = Character_Data.Status_3;
				Character_Data.Skills_A = Character_Data.Skills_3;
				Character_Data.Itens_A = Character_Data.Itens_3;
				Character_Data.Crystals_A = Character_Data.Crystals_3;
				Character_Data.Quests_A = Character_Data.Quests_3;
				Character_Data.ActiveQuest_A = Character_Data.ActiveQuest_3;
				Character_Data.Pet_A = Character_Data.Pet_3;
				Character_Data.Online_A = Character_Data.Online_3;
			}
			
			return Character_Data;
		}
		
		
		
		//Character Movement and Objects //
		
		public Sprite ReturnHairs(String hairName, String side, String walk, float posX, float posY) {			
			if(!hairName.contains("_")) {
				for(int i = 1; i <= 11; i++) {
					
					if(side.equals("Menu")) {
						if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i); spr_master.setPosition(posX, posY + 20.7f); spr_master.setSize(10, 15); return spr_master; }
					}
					
					if(inBattle && walk.equals("Stop")) {
						text = Character_Data.Battle_A;
						if(text.equals("yes_Right") && (pos == 1 || pos == 3 || pos == 5 || pos == 6)) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i + "battle2_right"); spr_master.setPosition(posX  + 6.5f, posY + 44f); spr_master.setSize(10, 15); return spr_master; } }
						if(text.equals("yes_Right") && (pos == 2 || pos == 4)) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i + "battle2_right"); spr_master.setPosition(posX  + 6.5f, posY + 44.2f); spr_master.setSize(10, 15); return spr_master; } }
						if(text.equals("yes_Left") && (pos == 1 || pos == 3 || pos == 5 || pos == 6)) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i + "battle2_left"); spr_master.setPosition(posX + 6.3f, posY + 44f); spr_master.setSize(10, 15); return spr_master; } }
						if(text.equals("yes_Left") && (pos == 2 || pos == 4)) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i + "battle2_left"); spr_master.setPosition(posX + 6.3f, posY + 44.2f); spr_master.setSize(10, 15); return spr_master; } }
					}
								
					if(side.equals("Front") || side.equals("Front-Left") || side.equals("Front-Right")) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i); spr_master.setPosition(posX + 7f, posY + 43.5f); spr_master.setSize(10, 15); return spr_master; } }
					if(side.equals("Right") || side.equals("Right-Front") || side.equals("Right-Back")) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i + "right"); spr_master.setPosition(posX + 5.5f, posY + 45f); spr_master.setSize(12, 15); return spr_master; } }
					if(side.equals("Left") || side.equals("Left-Front") || side.equals("Left-Back")) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i + "left"); spr_master.setPosition(posX + 7f, posY + 45f); spr_master.setSize(12, 15); return spr_master; } }
					if(side.equals("Back")|| side.equals("Left-Back") || side.equals("Left-Front") || side.equals("Back-Left") || side.equals("Back-Right")) { if(hairName.equals("hair" + i)) { spr_master = atlas_hairs.createSprite("hair" + i + "up"); spr_master.setPosition(posX + 7f, posY + 44f); spr_master.setSize(10, 14); return spr_master; } }	
				
				}
			}
			if(hairName.contains("_f")) {
				for(int i = 1; i <= 11; i++) {
					
					if(side.equals("Menu")) {
						if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "_f"); spr_master.setPosition(posX - 0.2f, posY + 19f); spr_master.setSize(10, 15); return spr_master; }
					}
					
					if(inBattle && walk.equals("Stop")) {
						text = Character_Data.Battle_A;
						if(text.equals("yes_Right") && (pos == 1 || pos == 3 || pos == 5 || pos == 6)) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "battle" + "_f" + "_right"); spr_master.setPosition(posX  + 3.8f, posY + 38.3f); spr_master.setSize(10, 15); return spr_master; } }
     					if(text.equals("yes_Right") && (pos == 2 || pos == 4)) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "battle" + "_f" + "_right"); spr_master.setPosition(posX  + 3.8f, posY + 38.1f); spr_master.setSize(10, 15); return spr_master; } }					
						if(text.equals("yes_Left") && (pos == 1 || pos == 3 || pos == 5 || pos == 6)) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "battle" + "_f" + "_left"); spr_master.setPosition(posX + 5.9f, posY + 38.3f); spr_master.setSize(10, 15); return spr_master; } }
						if(text.equals("yes_Left") && (pos == 2 || pos == 4)) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "battle" + "_f" + "_left"); spr_master.setPosition(posX + 5.9f, posY + 38.3f); spr_master.setSize(10, 15); return spr_master; } }
					}
					
					if(side.equals("Front") || side.equals("Front-Left") || side.equals("Front-Right")) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "_f"); spr_master.setPosition(posX + 1.7f, posY + 39.5f); spr_master.setSize(10, 15); return spr_master; } }
					if(side.equals("Right") || side.equals("Right-Front") || side.equals("Right-Back")) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "right" + "_f"); spr_master.setPosition(posX + 1.7f, posY + 39.6f); spr_master.setSize(12, 14); return spr_master; } }
					if(side.equals("Left") || side.equals("Left-Front") || side.equals("Left-Back")) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "left" + "_f"); spr_master.setPosition(posX + 0.9f, posY + 39.6f); spr_master.setSize(12, 14); return spr_master; } }
					if(side.equals("Back")|| side.equals("Left-Back") || side.equals("Left-Front") || side.equals("Back-Left") || side.equals("Back-Right")) { if(hairName.equals("hair" + i + "_f")) { spr_master = atlas_hairs.createSprite("hair" + i + "up" + "_f"); spr_master.setPosition(posX + 1.3f, posY + 40f); spr_master.setSize(10, 14); return spr_master; } }	
				}
			}
			
			return spr_master;
		}
	
		public Sprite MovChar(String set, String side,String walk, String type, float posX, float posY, int posInterject) {
			
			if(walk.equals("Walk") && side.equals("Left")) {
				fUsable = Float.parseFloat(Character_Data.PX_A);
				fUsable = fUsable - 0.8f;
				Character_Data.PX_A = String.valueOf(fUsable);
			}
			if(walk.equals("Walk") && side.equals("Right")) {
				fUsable = Float.parseFloat(Character_Data.PX_A);
				fUsable = fUsable + 0.8f;
				Character_Data.PX_A = String.valueOf(fUsable);
			}
			if(walk.equals("Walk") && side.equals("Front")) {
				fUsable = Float.parseFloat(Character_Data.PY_A);
				fUsable = fUsable - 0.8f;
				Character_Data.PY_A = String.valueOf(fUsable);
			}
			if(walk.equals("Walk") && side.equals("Back")) {
				fUsable = Float.parseFloat(Character_Data.PY_A);
				fUsable = fUsable + 0.8f;
				Character_Data.PY_A = String.valueOf(fUsable);
			}
			
			if(walk.equals("Walk") && side.equals("Left-Front")) {
				fUsable = Float.parseFloat(Character_Data.PX_A);
				fUsable = fUsable - 0.8f;
				Character_Data.PX_A = String.valueOf(fUsable);
				
				fUsable = Float.parseFloat(Character_Data.PY_A);
				fUsable = fUsable - 0.8f;
				Character_Data.PY_A = String.valueOf(fUsable);
				side = "Left";
			}
			if(walk.equals("Walk") && side.equals("Left-Back")) {
				fUsable = Float.parseFloat(Character_Data.PX_A);
				fUsable = fUsable - 0.8f;
				Character_Data.PX_A = String.valueOf(fUsable);
				
				fUsable = Float.parseFloat(Character_Data.PY_A);
				fUsable = fUsable + 0.8f;
				Character_Data.PY_A = String.valueOf(fUsable);
				side = "Left";
			}
			if(walk.equals("Walk") && side.equals("Back-Left")) {
				fUsable = Float.parseFloat(Character_Data.PX_A);
				fUsable = fUsable - 0.8f;
				Character_Data.PX_A = String.valueOf(fUsable);
				
				fUsable = Float.parseFloat(Character_Data.PY_A);
				fUsable = fUsable + 0.8f;
				Character_Data.PY_A = String.valueOf(fUsable);
				side = "Back";
			}
			if(walk.equals("Walk") && side.equals("Back-Right")) {
				fUsable = Float.parseFloat(Character_Data.PX_A);
				fUsable = fUsable + 0.8f;
				Character_Data.PX_A = String.valueOf(fUsable);
				
				fUsable = Float.parseFloat(Character_Data.PY_A);
				fUsable = fUsable + 0.8f;
				Character_Data.PY_A = String.valueOf(fUsable);
				side = "Back";
			}
			if(walk.equals("Walk") && side.equals("Right-Back")) {
				fUsable = Float.parseFloat(Character_Data.PX_A);
				fUsable = fUsable + 0.8f;
				Character_Data.PX_A = String.valueOf(fUsable);
				
				fUsable = Float.parseFloat(Character_Data.PY_A);
				fUsable = fUsable + 0.8f;
				Character_Data.PY_A = String.valueOf(fUsable);
				side = "Right";
			}
			if(walk.equals("Walk") && side.equals("Right-Front")) {
				fUsable = Float.parseFloat(Character_Data.PX_A);
				fUsable = fUsable + 0.8f;
				Character_Data.PX_A = String.valueOf(fUsable);
				
				fUsable = Float.parseFloat(Character_Data.PY_A);
				fUsable = fUsable - 0.8f;
				Character_Data.PY_A = String.valueOf(fUsable);
				side = "Right";
			}
			if(walk.equals("Walk") && side.equals("Front-Left")) {
				fUsable = Float.parseFloat(Character_Data.PX_A);
				fUsable = fUsable - 0.8f;
				Character_Data.PX_A = String.valueOf(fUsable);
				
				fUsable = Float.parseFloat(Character_Data.PY_A);
				fUsable = fUsable - 0.8f;
				Character_Data.PY_A = String.valueOf(fUsable);
				side = "Front";
			}
			if(walk.equals("Walk") && side.equals("Front-Right")) {
				fUsable = Float.parseFloat(Character_Data.PX_A);
				fUsable = fUsable + 0.8f;
				Character_Data.PX_A = String.valueOf(fUsable);
				
				fUsable = Float.parseFloat(Character_Data.PY_A);
				fUsable = fUsable - 0.8f;
				Character_Data.PY_A = String.valueOf(fUsable);
				side = "Front";
			}
			
			if((walk.equals("Walk") && !inBattle) || (walk.equals("Walk")) && inBattle){
				frameMove++;
				if(frameMove >= 1 && frameMove <= 10) { 					
					pos = 1; 
				}
				
				if(frameMove >= 10 && frameMove <= 20) { 
					pos = 2; 
				}
				
				if(frameMove >= 20 && frameMove <= 30) { 
					pos = 1; 
				}
				
				if(frameMove >= 30 && frameMove <= 40) { 
					pos = 3; 
				}
				
				if(frameMove > 40) { 
					frameMove = 1; 
				}
			}
			
			if(!walk.equals("Walk") && inBattle){
				frameMove++;
				if(frameMove >= 1 && frameMove <= 10) { 					
					pos = 1; 
				}
				
				if(frameMove >= 10 && frameMove <= 20) { 
					pos = 2; 
				}
				
				if(frameMove >= 20 && frameMove <= 30) { 
					pos = 3; 
				}
				
				if(frameMove >= 30 && frameMove <= 40) { 
					pos = 4; 
				}
				
				if(frameMove >= 40 && frameMove <= 50) { 
					pos = 5; 
				}
				
				if(frameMove >= 50 && frameMove <= 60) { 
					pos = 6; 
				}
				
				if(frameMove > 60) { 
					frameMove = 1; 
				}
			}
			
			if(walk.equals("Stop") && !inBattle) {
				pos = 1;
			}
			
			if(walk.equals("Stop") && side.equals("Left-Front")) {
				side = "Left";
			}
			if(walk.equals("Stop") && side.equals("Left-Back")) {
				side = "Left";
			}
			if(walk.equals("Stop") && side.equals("Back-Left")) {
				side = "Back";
			}
			if(walk.equals("Stop") && side.equals("Back-Right")) {
				side = "Back";
			}
			if(walk.equals("Stop") && side.equals("Right-Back")) {
				side = "Right";
			}
			if(walk.equals("Stop") && side.equals("Right-Front")) {
				side = "Right";
			}
			if(walk.equals("Stop") && side.equals("Front-Left")) {
				side = "Front";
			}
			if(walk.equals("Stop") && side.equals("Front-Right")) {
				side = "Front";
			}
			
			if(posInterject > 0) { pos = posInterject; }
			//pos = 3;
			
			//attackFrame = false;
			
			//Para a arma
			playerbattleframe = pos;
			
			//MASC. /////////////////////////////////////////////////  
			if(set.equals("basic_set_male")) {
				
				//SET
				//BATTLE
				if(inBattle && walk.equals("Stop") && !type.equals("Menu")) {
					text = Character_Data.Battle_A;
					if(Character_Data.Job_A.equals("Novice")) {
						if(attackFrame && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_meleeAttack_right"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
						if(attackFrame && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_meleeAttack_left"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
						if(pos == 1 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
						if(pos == 2 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
						if(pos == 3 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }	
						if(pos == 4 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
						if(pos == 5 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
						if(pos == 6 && text.equals("yes_Right")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }	
						
						if(pos == 1 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
						if(pos == 2 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
						if(pos == 3 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
						if(pos == 4 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
						if(pos == 5 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
						if(pos == 6 && text.equals("yes_Left")) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
					}
				}
				
				//Menu
				if(type.equals("Menu")) { 
					spr_master = atlas_basic_male_set.createSprite("basic_set_male_front1"); spr_master.setPosition(posX - 7.5f, posY - 10.5f);  spr_master.setSize(25, 36);
					return spr_master;
				}
				
				//Front
				if(side.equals("Front") && pos == 1) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_front1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
				if(side.equals("Front") && pos == 2) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_front2"); spr_master.setPosition(posX - 0.1f, posY + 12.3f); spr_master.setSize(25, 36); return spr_master; }
				if(side.equals("Front") && pos == 3) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_front3"); spr_master.setPosition(posX - 0.1f, posY + 12.4f); spr_master.setSize(25, 36); return spr_master; }
				
				//back
				if(side.equals("Back") && pos == 1) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_back1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
				if(side.equals("Back") && pos == 2) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_back2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
				if(side.equals("Back") && pos == 3) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_back3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(25, 36); return spr_master; }
				
				//right
				if(side.equals("Right") && pos == 1) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_right1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
				if(side.equals("Right") && pos == 2) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_right2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
				if(side.equals("Right") && pos == 3) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_right3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master;}
				
				//left
				if(side.equals("Left") && pos == 1) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);   spr_master.setSize(25, 36); return spr_master; }
				if(side.equals("Left") && pos == 2) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36); return spr_master; }
				if(side.equals("Left") && pos == 3) { spr_master = atlas_basic_male_set.createSprite("basic_set_male_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(25, 36);  return spr_master; }
				
			}
			
			//FEM. /////////////////////////////////////////////////
			if(set.equals("basic_set_female")) {
				//Battle
				if(inBattle && walk.equals("Stop") && !type.equals("Menu")) {
					text = Character_Data.Battle_A;
					if(Character_Data.Job_A.equals("Novice")) {
						if(attackFrame && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_meleeAttack_right"); spr_master.setPosition(posX - 4f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
						if(attackFrame && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_meleeAttack_left"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
						if(pos == 1 && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right1"); spr_master.setPosition(posX - 4f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
						if(pos == 2 && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right2"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
						if(pos == 3 && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right3"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }	
						if(pos == 4 && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right3"); spr_master.setPosition(posX - 4f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
						if(pos == 5 && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right2"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
						if(pos == 6 && text.equals("yes_Right")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_right1"); spr_master.setPosition(posX - 4f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }	
						
						if(pos == 1 && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
						if(pos == 2 && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
						if(pos == 3 && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
						if(pos == 4 && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_left3"); spr_master.setPosition(posX - 0.6f, posY + 12.5f);  spr_master.setSize(24, 33); return spr_master; }
						if(pos == 5 && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_left2"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
						if(pos == 6 && text.equals("yes_Left")) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_swordman_left1"); spr_master.setPosition(posX - 0.6f, posY + 12.5f); spr_master.setSize(24, 33); return spr_master; }
					}
				}
				
				//Menu
				if(type.equals("Menu")) { 
					spr_master = atlas_basic_female_set.createSprite("basic_set_female_front1"); spr_master.setPosition(posX - 7.5f, posY - 7);  spr_master.setSize(25, 33);
					return spr_master;
				}
				
				//Front
				if(side.equals("Front") && pos == 1) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_front1"); spr_master.setPosition(posX - 5.5f, posY + 13.5f);  spr_master.setSize(25, 33); return spr_master; }
				if(side.equals("Front") && pos == 2) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_front2"); spr_master.setPosition(posX - 5.5f, posY + 13.5f); spr_master.setSize(25, 33); return spr_master; }
				if(side.equals("Front") && pos == 3) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_front3"); spr_master.setPosition(posX - 5.5f, posY + 13.5f); spr_master.setSize(25, 33); return spr_master; }
				
				//back
				if(side.equals("Back") && pos == 1) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_back1"); spr_master.setPosition(posX - 4.5f, posY + 14f); spr_master.setSize(23, 33); return spr_master; }
				if(side.equals("Back") && pos == 2) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_back2"); spr_master.setPosition(posX - 4.5f, posY + 15f); spr_master.setSize(23, 33); return spr_master; }
				if(side.equals("Back") && pos == 3) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_back3"); spr_master.setPosition(posX - 4.5f, posY + 14f); spr_master.setSize(23, 33); return spr_master; }
				
				//right
				if(side.equals("Right") && pos == 1) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_right1"); spr_master.setPosition(posX - 3.3f, posY + 13f);  spr_master.setSize(24, 34); return spr_master; }
				if(side.equals("Right") && pos == 2) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_right2"); spr_master.setPosition(posX - 3.8f, posY + 13f);  spr_master.setSize(24, 34); return spr_master; }
				if(side.equals("Right") && pos == 3) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_right3"); spr_master.setPosition(posX - 3.3f, posY + 13f);  spr_master.setSize(24, 34); return spr_master;}
				
				//left
				if(side.equals("Left") && pos == 1) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_left1"); spr_master.setPosition(posX - 5.3f, posY + 12.5f);   spr_master.setSize(24, 34); return spr_master; }
				if(side.equals("Left") && pos == 2) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_left2"); spr_master.setPosition(posX - 4.5f, posY + 12.5f);  spr_master.setSize(24, 34); return spr_master; }
				if(side.equals("Left") && pos == 3) { spr_master = atlas_basic_female_set.createSprite("basic_set_female_left3"); spr_master.setPosition(posX - 5.3f, posY + 12.5f);   spr_master.setSize(24, 34); return spr_master; }
				
			}
			
			return spr_master;
		}
		
		//Recupera Position
		public int RecuperaPosition() {
			return playerbattleframe;
		}
		
		public Sprite ShowWeapon(String side,String walk, String type, float posX, float posY) {
			if(inBattle && walk.equals("Stop") && !type.equals("Menu")) {
				text = Character_Data.Battle_A;
				weapon = Character_Data.Weapon_A;
					if(Character_Data.Job_A.equals("Novice")) { 
							if(weapon.equals("basic_knife")) {
								
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 8.9f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 5f, posY + 30);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("M")) {
								
								if(attackFrame) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 15f, posY + 19);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 5f, posY + 30);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 30);
									return spr_master;
								}							
							}
							
							if(text.equals("yes_Right") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_attack_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 4f, posY + 15f);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 11.9f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_right");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 12f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_side_right");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX - 7f, posY + 24);
									return spr_master;
								}
							}
							if(text.equals("yes_Left") && Character_Data.Sex_A.equals("F")) {
								
								if(attackFrame) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_attack_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX - 14f, posY + 17);
									return spr_master;
								}
								
								if(playerbattleframe == 1 || playerbattleframe == 6) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26);
									return spr_master;
								}
								
								if(playerbattleframe == 2 || playerbattleframe == 5) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_left");
									spr_master.setSize(30,37);
									spr_master.setPosition(posX + 2f, posY + 26.2f);
									return spr_master;
								}
								
								if(playerbattleframe == 3 || playerbattleframe == 4) {
									spr_master = atlas_nKnifes.createSprite("basic_knife_side_left");
									spr_master.setSize(25,39);
									spr_master.setPosition(posX + 2f, posY + 24);
									return spr_master;
								}							
							}
						}
					}
				}
					
			
			return null;
		}
		
		
		// Scenario Objects //
		public Sprite LoadObject(String name) {
			
			if(name.equals("metrobackword1")) {
				spr_master = atlas_objectsMetro.createSprite("metrobackword1");
			}
			if(name.equals("metrobackword2")) {
				spr_master = atlas_objectsMetro.createSprite("metrobackword2");
			}
			if(name.equals("metrobackword3")) {
				spr_master = atlas_objectsMetro.createSprite("metrobackword3");
			}
			
			if(name.equals("metroTV1")) {
				spr_master = atlas_objectsMetro.createSprite("metroTV1");
			}
			if(name.equals("metroTV2")) {
				spr_master = atlas_objectsMetro.createSprite("metroTV2");
			}
			if(name.equals("metroTV3")) {
				spr_master = atlas_objectsMetro.createSprite("metroTV3");
			}
			
			return spr_master;
		}
		
		
		// Interfaces and Screens //
		
		public ArrayList<Damage> ExibeDanos(){
			for(countA = 0; countA < lstDamage.size(); countA++){ 
				if(lstDamage.get(countA).time > 0){
			     lstDamage.get(countA).areaY = lstDamage.get(countA).areaY + 0.5f;
				 lstDamage.get(countA).time = lstDamage.get(countA).time - 1;
			     }
				 else {
					 lstDamage.remove(countA);
				 }
			}
			
			return lstDamage;
		}
		
		public String[] LoadShowCharSelectScreen() {	
			
			charData[0] = Character_Data.Name_1;
			charData[1] = Character_Data.Name_2;
			charData[2] = Character_Data.Name_3;
			
			if(!Character_Data.Name_1.equals("none")) { charData[3] = Character_Data.Set_1; }
			if(!Character_Data.Name_2.equals("none")) { charData[6] = Character_Data.Set_2; }
			if(!Character_Data.Name_3.equals("none")) { charData[9] = Character_Data.Set_3; }
			
			if(!Character_Data.Name_1.equals("none")) { charData[12] = Character_Data.Hair_1;  }
			if(!Character_Data.Name_2.equals("none")) { charData[13] = Character_Data.Hair_2;  }
			if(!Character_Data.Name_3.equals("none")) { charData[14] = Character_Data.Hair_3;  }
			
			if(!Character_Data.Name_1.equals("none")) { charData[20] = Character_Data.Name_1; charData[21] = Character_Data.Level_1; charData[22] = Character_Data.Job_1; charData[23] = Character_Data.Map_1; }
			if(!Character_Data.Name_2.equals("none")) { charData[30] = Character_Data.Name_2; charData[31] = Character_Data.Level_2; charData[32] = Character_Data.Job_2; charData[33] = Character_Data.Map_2; }
			if(!Character_Data.Name_3.equals("none")) { charData[40] = Character_Data.Name_3; charData[41] = Character_Data.Level_3; charData[42] = Character_Data.Job_3; charData[43] = Character_Data.Map_3; }
			
			if(!Character_Data.Name_1.equals("none")) { charData[45] = Character_Data.Sex_1;  }
			if(!Character_Data.Name_2.equals("none")) { charData[46] = Character_Data.Sex_2;  }
			if(!Character_Data.Name_3.equals("none")) { charData[47] = Character_Data.Sex_3;  }
			
			return charData;
		}
		
		public void DeleteCharacter(int numchar) {
			LoadData();
			if(numchar == 1) {
			Character_Data.setName_1("none");
			Character_Data.setPX_1("45");
			Character_Data.setPY_1("85"); 
			SaveData();
			}
			
			if(numchar == 2) {
			Character_Data.setName_2("none");
			Character_Data.setPX_2("45");
			Character_Data.setPY_2("85"); 
			SaveData();
			}
			
			if(numchar == 3) {
			Character_Data.setName_3("none");
			Character_Data.setPX_3("45");
			Character_Data.setPY_3("85"); 
			SaveData();
			}		
		}
		
		public boolean CheckExistCharacter(int numchar) {
			if(numchar == 1) {
				if(Character_Data.Name_1.equals("none")) { return false; }
			}
			if(numchar == 2) {
				if(Character_Data.Name_2.equals("none")) { return false; }
			}
			if(numchar == 3) {
				if(Character_Data.Name_3.equals("none")) { return false; }
			}
			return true;
		}
		
		public String[] CameraSettings(String map) {
			String[] cameraSet = new String[3];
			if(map.equals("MetroStation")) {
				cameraSet[0] = "400";
				cameraSet[1] = "100";
				return cameraSet;
			}
			
			if(map.equals("Streets305")) {
				cameraSet[0] = "400";
				cameraSet[1] = "1200";
				return cameraSet;
			}
			
			return cameraSet;
		}
		
		public void AtualizaCameraX(float cameraposX) {
			fX = cameraposX;
		}
		public void AtualizaCameraY(float cameraposY) {
			fY = cameraposY;
		}
		
		public Sprite InterfaceMetroStation(String item, String complement) {
			
			if(item.equals("PlayerTag")) {
				spr_master = atlas_gameplay_interface.createSprite("tagplayer");
				spr_master.setSize(45, 35);
				spr_master.setPosition(fX - 98, fY + 68);
				return spr_master; 
			}
			
			if(item.equals("Portrait")) {
				spr_master = atlas_hairs.createSprite(complement);
				spr_master.setSize(14, 19);
				spr_master.setPosition(fX - 99, fY + 78);
				return spr_master; 
			}
			
			if(item.equals("Hotcrossbar")) {
				spr_master = atlas_gameplay_interface.createSprite("hotcrossbar");
				spr_master.setSize(55, 40);
				spr_master.setPosition(fX + 45, fY - 95);
				return spr_master;
			}
			
			if(item.equals("Backanalog")) {
				spr_master = atlas_gameplay_interface.createSprite("controlerbackground");
				spr_master.setSize(40, 50);
				spr_master.setPosition(fX - 78, fY - 80);
				return spr_master;
			}
			
			if(item.equals("Analog") && complement.equals("Stop")) {
				spr_master = atlas_gameplay_interface.createSprite("controllertouch");
				spr_master.setSize(20, 25);
				spr_master.setPosition(fX - 68, fY - 67);
				return spr_master;
			}
			
			if(item.equals("Analog") && complement.equals("Right")) {
				spr_master = atlas_gameplay_interface.createSprite("controllertouch");
				spr_master.setSize(20, 25);
				spr_master.setPosition(fX - 58, fY - 67);
				return spr_master;
			}
			
			if(item.equals("Analog") && complement.equals("Left")) {
				spr_master = atlas_gameplay_interface.createSprite("controllertouch");
				spr_master.setSize(20, 25);
				spr_master.setPosition(fX - 78, fY - 67);
				return spr_master;
			}
			
			if(item.equals("Analog") && complement.equals("Front")) {
				spr_master = atlas_gameplay_interface.createSprite("controllertouch");
				spr_master.setSize(20, 25);
				spr_master.setPosition(fX - 68, fY - 81);
				return spr_master;
			}
			if(item.equals("Analog") && complement.equals("Back")) {
				spr_master = atlas_gameplay_interface.createSprite("controllertouch");
				spr_master.setSize(20, 25);
				spr_master.setPosition(fX - 68, fY - 55);
				return spr_master;
			}
			
			return spr_master;
		}
		
		public Sprite InterfaceStreets305(String item, String complement) {
			
			if(item.equals("PlayerTag")) {
				spr_master = atlas_gameplay_interface.createSprite("tagplayer");
				spr_master.setSize(45, 35);
				spr_master.setPosition(fX - 100, fY + 95);
				return spr_master; 
			}
			
			if(item.equals("deathmsg")) {
				spr_master = atlas_gameplay_interface.createSprite("mortemsg");
				spr_master.setSize(45, 35);
				spr_master.setPosition(fX - 15, fY + 8);
				return spr_master; 
			}
			
			if(item.equals("Portrait")) {
				spr_master = atlas_hairs.createSprite(complement);
				spr_master.setSize(14, 19);
				spr_master.setPosition(fX - 101, fY + 106);
				return spr_master; 
			}
			
			if(item.equals("Hotcrossbar")) {
				spr_master = atlas_gameplay_interface.createSprite("hotcrossbar");
				spr_master.setSize(55, 38);
				spr_master.setPosition(fX + 45, fY - 70);
				return spr_master;
			}
			
			if(item.equals("Backanalog")) {
				spr_master = atlas_gameplay_interface.createSprite("controlerbackground");
				spr_master.setSize(40, 50);
				spr_master.setPosition(fX - 78, fY - 50);
				return spr_master;
			}
			
			if(item.equals("Analog") && complement.equals("Stop")) {
				spr_master = atlas_gameplay_interface.createSprite("controllertouch");
				spr_master.setSize(20, 25);
				spr_master.setPosition(fX - 68, fY - 37);
				return spr_master;
			}
			
			if(item.equals("flagStreet305")) {
				spr_master = atlas_gameplay_interface.createSprite("flagStreets305");
				spr_master.setSize(30, 15);
				spr_master.setPosition(fX + 70, fY + 115);
				return spr_master;
			}
			
			if(item.equals("Analog") && complement.equals("Right")) {
				spr_master = atlas_gameplay_interface.createSprite("controllertouch");
				spr_master.setSize(20, 25);
				spr_master.setPosition(fX - 58, fY - 37);
				return spr_master;
			}
			
			if(item.equals("Analog") && complement.equals("Left")) {
				spr_master = atlas_gameplay_interface.createSprite("controllertouch");
				spr_master.setSize(20, 25);
				spr_master.setPosition(fX - 78, fY - 37);
				return spr_master;
			}
			
			if(item.equals("Analog") && complement.equals("Front")) {
				spr_master = atlas_gameplay_interface.createSprite("controllertouch");
				spr_master.setSize(20, 25);
				spr_master.setPosition(fX - 68, fY - 51);
				return spr_master;
			}
			if(item.equals("Analog") && complement.equals("Back")) {
				spr_master = atlas_gameplay_interface.createSprite("controllertouch");
				spr_master.setSize(20, 25);
				spr_master.setPosition(fX - 68, fY - 25);
				return spr_master;
			}
			if(item.equals("AreaCombate")) {
				spr_master = atlas_gameplay_interface.createSprite("btnAreaBatalha");
				spr_master.setSize(25, 15);
				spr_master.setPosition(fX - 55f, fY + 115);
				return spr_master;
			}
			if(item.equals("tabelaBatalha")) {
				spr_master = atlas_gameplay_interface.createSprite("tabelaBatalha");
				spr_master.setSize(100, 100);
				spr_master.setPosition(fX, fY);
				return spr_master;
			}
			if(item.equals("autoATK") && complement.equals("ON")) {
				spr_master = atlas_gameplay_interface.createSprite("btnAutoAttackOn");
				spr_master.setSize(8, 16);
				spr_master.setPosition(fX + 73, fY - 49);
				return spr_master;
			}
			if(item.equals("autoATK") && complement.equals("OFF")) {
				spr_master = atlas_gameplay_interface.createSprite("btnAutoAttackOff");
				spr_master.setSize(8, 16);
				spr_master.setPosition(fX + 73, fY - 49);
				return spr_master;
			}
			if(item.equals("AtkBtn")) {
				spr_master = atlas_gameplay_interface.createSprite("hotAtk");
				spr_master.setSize(8, 16);
				spr_master.setPosition(fX + 46, fY - 68.5f);
				return spr_master;
			}
			if(item.equals("ActionBtn")) {
				spr_master = atlas_gameplay_interface.createSprite("btnAcao");
				spr_master.setSize(8, 16);
				spr_master.setPosition(fX + 91, fY - 49f);
				return spr_master;
			}
			if(item.equals("textovershop1")) {
				spr_master = atlas_objectsMetro.createSprite("anibarshop1");
				spr_master.setSize(82.5f, 14);
				spr_master.setPosition(184, -8);
				return spr_master;
			}
			if(item.equals("textovershop2")) {
				spr_master = atlas_objectsMetro.createSprite("anibarshop2");
				spr_master.setSize(82.5f, 14);
				spr_master.setPosition(184, -8);
				return spr_master;
			}
			if(item.equals("textovershop3")) {
				spr_master = atlas_objectsMetro.createSprite("anibarshop3");
				spr_master.setSize(82.5f, 14);
				spr_master.setPosition(184, -8);
				return spr_master;
			}
			if(item.equals("tripleAttackbtn")) {
				spr_master = atlas_gameplay_interface.createSprite("btntripleattack");
				spr_master.setSize(8, 16);
				spr_master.setPosition(fX + 55, fY - 68.5f);
				return spr_master;
			}
			if(item.equals("tripleAttackbtnMenu")) {
				spr_master = atlas_gameplay_interface.createSprite("btntripleattack");
				spr_master.setSize(8, 16);
				spr_master.setPosition(fX - 55, fY + 22);
				return spr_master;
			}
			if(item.equals("SodaMachine")) {
				spr_master = atlas_shop.createSprite("refrishop");
				spr_master.setSize(90, 140);
				spr_master.setPosition(fX, fY - 20);
				return spr_master;
			}
			
			if(item.equals("QuestBaloon1")) {
				spr_master = atlas_gameplay_interface.createSprite("avisoMissao1");
				spr_master.setSize(12, 12);
				spr_master.setPosition(64, -40);
				return spr_master;
			}
			if(item.equals("QuestBaloon2")) {
				spr_master = atlas_gameplay_interface.createSprite("avisoMissao2");
				spr_master.setSize(12, 12);
				spr_master.setPosition(64, -40);
				return spr_master;
			}
			if(item.equals("QuestBaloon3")) {
				spr_master = atlas_gameplay_interface.createSprite("avisoMissao3");
				spr_master.setSize(12, 12);
				spr_master.setPosition(64, -40);
				return spr_master;
			}
			if(item.equals("QuestBaloon4")) {
				spr_master = atlas_gameplay_interface.createSprite("avisoMissao4");
				spr_master.setSize(12, 12);
				spr_master.setPosition(64, -40);
				return spr_master;
			}
			if(item.equals("QuestBaloon5")) {
				spr_master = atlas_gameplay_interface.createSprite("avisoMissao5");
				spr_master.setSize(12, 12);
				spr_master.setPosition(64, -40);
				return spr_master;
			}
			if(item.equals("QuestBaloon6")) {
				spr_master = atlas_gameplay_interface.createSprite("avisoMissao6");
				spr_master.setSize(12, 12);
				spr_master.setPosition(64, -40);
				return spr_master;
			}
			
			if(item.equals("questBar")) {
				spr_master = atlas_gameplay_interface.createSprite("bar_text");
				spr_master.setSize(205,50);
				spr_master.setPosition(fX - 100, fY - 72);
				return spr_master;
			}
			
			return spr_master;
		}
		
		
		public Sprite ItensMenu(String item, String complement) {
			
			if(item.equals("Menu") && complement.equals("Main")) {
				spr_master = atlas_gameplay_interface.createSprite("menu");
				spr_master.setSize(15,120);
				spr_master.setPosition(fX + 85.5f, fY - 30.5f);
			}
			if(item.equals("Menu") && complement.equals("Status")) {
				spr_master = atlas_gameplay_interface.createSprite("menustatus");
				spr_master.setSize(130,130);
				spr_master.setPosition(fX - 60.5f, fY - 40.5f);
			}
			if(item.equals("Menu") && complement.equals("Itens")) {
				spr_master = atlas_gameplay_interface.createSprite("menuitens");
				spr_master.setSize(130,130);
				spr_master.setPosition(fX - 60.5f, fY - 40.5f);
			}
			if(item.equals("Menu") && complement.equals("Skills")) {
				spr_master = atlas_gameplay_interface.createSprite("menuskills");
				spr_master.setSize(130,130);
				spr_master.setPosition(fX - 60.5f, fY - 40.5f);
			}
			if(item.equals("Menu") && complement.equals("Social")) {
				spr_master = atlas_gameplay_interface.createSprite("menusocial");
				spr_master.setSize(130,130);
				spr_master.setPosition(fX - 60.5f, fY - 40.5f);
			}
			if(item.equals("Menu") && complement.equals("Config")) {
				spr_master = atlas_gameplay_interface.createSprite("menuopcoes");
				spr_master.setSize(130,130);
				spr_master.setPosition(fX - 60.5f, fY - 40.5f);
			}
			if(item.equals("Menu") && complement.equals("Batalha")) {
				spr_master = atlas_gameplay_interface.createSprite("menubatalha");
				spr_master.setSize(130,130);
				spr_master.setPosition(fX - 60.5f, fY - 40.5f);
			}
			
			return spr_master;
		}
		
		
		// Monsters //
		public void CarregaMonstrosMapa(String mapa) {
			lstMonsters.clear();
			if(mapa.equals("Streets305")) {
				lstMonsters.add(mobContainer.GetMonster("slimeA", "Streets305"));
			}
		}
				
		public ArrayList<Sprite> ExibeMonstros(float playerX, float playerY) {
			
			lstSprites.clear();
			
			for(countA = 0; countA < lstMonsters.size(); countA++){	
				
				if(lstMonsters.get(countA).AGRESSIVE.equals("no")) { mobContainer = mobContainer.FrameAndMovement(lstMonsters.get(countA), pX, pY);}
				if(lstMonsters.get(countA).AGRESSIVE.equals("yes")) { mobContainer = mobContainer.FrameAndMovementAgressive(lstMonsters.get(countA), pX, pY);}
				
				mobX = Float.parseFloat(mobContainer.PX);
				mobY = Float.parseFloat(mobContainer.PY);
				
				if(mobX <= -48) { mobX = -48; }
				if(mobX >= 424) { mobX = 422; }
				if(mobY > 50) { mobY = 50; }
				if(mobY < -357) { mobY = -354; }
				
				
					if(mobContainer.NAME.equals("slime")) { atlas_Mob = atlas_MonsterSlime; }
				
					if(lstMonsters.get(countA).SIDE.equals("right")) {
						
							if(mobContainer.FRAME.equals("1")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "1_right");   
								spr_master.setPosition(mobX, mobY);
							}
							if(mobContainer.FRAME.equals("2")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "2_right");   
								spr_master.setPosition(mobX, mobY);
							}
							if(mobContainer.FRAME.equals("3")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "3_right");   
								spr_master.setPosition(mobX, mobY);
							}
							if(mobContainer.FRAME.equals("4")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "4_right");   
								spr_master.setPosition(mobX, mobY);
							}
							if(mobContainer.FRAME.equals("5")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "1_damage_right");   
								spr_master.setPosition(mobX, mobY);
							}
						}
					
				        if(lstMonsters.get(countA).SIDE.equals("left")) {
							if(mobContainer.FRAME.equals("1")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "1_left");   
								spr_master.setPosition(mobX, mobY);
							}
							if(mobContainer.FRAME.equals("2")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "2_left");   
								spr_master.setPosition(mobX, mobY);
							}
							if(mobContainer.FRAME.equals("3")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "3_left");   
								spr_master.setPosition(mobX, mobY);
							}
							if(mobContainer.FRAME.equals("4")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "4_left");   
								spr_master.setPosition(mobX, mobY);
							}
							if(mobContainer.FRAME.equals("5")) { 
								spr_master = atlas_Mob.createSprite(mobContainer.NAME + "1_damage_left");   
								spr_master.setPosition(mobX, mobY);
							}
						}
					
					
					
					mobX = Float.parseFloat(mobContainer.WIDTH);
					mobY = Float.parseFloat(mobContainer.HEIGHT);
					
					spr_master.setSize(mobX, mobY);
					
					lstSprites.add(spr_master);
			}
			
			return lstSprites;
		}
		
		public ArrayList<String> ExibeNomesMonstros(){
			
			lstNomes.clear();
			
			for(countA = 0; countA < lstMonsters.size(); countA++){		
			
				lstNomes.add(lstMonsters.get(countA).NAME + "/" + lstMonsters.get(countA).HP + "/" + lstMonsters.get(countA).HPMAX + "/" + lstMonsters.get(countA).PX + "/" + lstMonsters.get(countA).PY);
			}
			
			return lstNomes;
		}
		
		
		
		//Respawn Monstros
		public void RespawnMonstros() {
			int respawn = 0;
			int respawnMax = 0;
			int HPMAX = 0;
			int pXRandom = 0;
			int pYRandom = 0;
			float pYmob = 0;
			for(countA = 0; countA < lstMonsters.size(); countA++){ 
				if(lstMonsters.get(countA).LOCKDEATH.equals("yes")){
					
					lstMonsters.get(countA).PX = "-1200";
					lstMonsters.get(countA).PY = "-1000";
					
					respawn = Integer.parseInt(lstMonsters.get(countA).RESPAWN);
					respawnMax = Integer.parseInt(lstMonsters.get(countA).RESPAWNMAX);					
					respawn--;
					lstMonsters.get(countA).RESPAWN = String.valueOf(respawn);
					if(respawn < 0) {
						pXRandom = randnumber.nextInt(422);
						pYRandom = randnumber.nextInt(370);
						pYmob = pYRandom;
						pYmob = pYmob - (pYmob * 2);
						
						lstMonsters.get(countA).PX = String.valueOf(pXRandom);
						lstMonsters.get(countA).PY = String.valueOf(pYmob);
						lstMonsters.get(countA).RESPAWN = lstMonsters.get(countA).RESPAWNMAX;
						lstMonsters.get(countA).HP = lstMonsters.get(countA).HPMAX;
						lstMonsters.get(countA).MP = lstMonsters.get(countA).MPMAX;
						lstMonsters.get(countA).LOCKDEATH = "no";
					}
				}
			}
		}
		
		
		// Battle Settings //
		public void VerificaAttack(String AutoAttack, String ManualAttack) {
			
			pX = Float.parseFloat(Character_Data.PX_A);
			pY = Float.parseFloat(Character_Data.PY_A);
			playerHP  = Integer.parseInt(Character_Data.HP_A);
			playerAtk  = Integer.parseInt(Character_Data.Atk_A);
			playerDef  = Integer.parseInt(Character_Data.Def_A);
			playerStrenght  = Integer.parseInt(Character_Data.Strengh_A);
			playerAgility  = Integer.parseInt(Character_Data.Agility_A);
			playerLucky = Integer.parseInt(Character_Data.Lucky_A);
			playerDextery  = Integer.parseInt(Character_Data.Dextery_A);
			playerMind = Integer.parseInt(Character_Data.Mind_A);
			attackCooldown--;
			
			pX = pX + 7;
			pY = pY + 11;
			
			//Montando zona de attack do jogador
			if(Character_Data.Job_A.equals("Novice") ||
			   Character_Data.Job_A.equals("Swordman") ||
			   Character_Data.Job_A.equals("Merchant") ||
			   Character_Data.Job_A.equals("Thief") ||
			   Character_Data.Job_A.equals("Monk")) {
				
				pAttackZoneXPlus = pX + 20;
				pAttackZoneXMinus = pX - 20;
				pAttackZoneYPlus = pY + 30;
				pAttackZoneYMinus = pY - 30;		
			}
			
			if(Character_Data.Job_A.equals("Gunner") ||
			   Character_Data.Job_A.equals("Magician")) {
				pAttackZoneXPlus = pX + 60;
				pAttackZoneXMinus = pX - 60;
				pAttackZoneYPlus = pY + 70;
				pAttackZoneYPlus = pY - 70;
			}
			
			if(playerManualAtkDelay > 0) { playerManualAtkDelay--; }
			if(playerManualAtkDelay < 10) { attackFrame = false; }
			if(ManualAttack.equals("yes") && playerManualAtkDelay == 0){
				for(countA = 0; countA < lstMonsters.size(); countA++){ 		  //Verifica Monstro Target dentro da lista			
					if(lstMonsters.get(countA).ID.equals(Character_Data.Target_A)) {
						mobX = Float.parseFloat(lstMonsters.get(countA).PX) + (Float.parseFloat(lstMonsters.get(countA).WIDTH) / 2);
						mobY = Float.parseFloat(lstMonsters.get(countA).PY) + (Float.parseFloat(lstMonsters.get(countA).HEIGHT) / 2);
						if((mobX > pAttackZoneXMinus && mobX < pAttackZoneXPlus) && (mobY > pAttackZoneYMinus && mobY < pAttackZoneYPlus) ) {						
								
							//Posiciona personagem baseado no monstro
							if(pX > mobX) { Character_Data.Battle_A = "yes_Left"; attackFrame = true; lstMonsters.get(countA).FRAME = "5"; }
							if(pX < mobX) { Character_Data.Battle_A = "yes_Right"; attackFrame = true; lstMonsters.get(countA).FRAME = "5"; }
							
							mobHP = Integer.parseInt(lstMonsters.get(countA).HP);		
							mobHP = mobHP - 1;		

							Damage danoMob = new Damage();
							danoMob.areaX = Math.round(mobX);
							danoMob.areaY = Math.round(mobY);
							danoMob.dano = String.valueOf("1");
							danoMob.time = 60;
							danoMob.Color = "Yellow";
							danoMob.Descritivo = "Ataque";
							lstDamage.add(danoMob);
                                
							if(mobHP <= 0 && lstMonsters.get(countA).LOCKDEATH.equals("no")) { 
								GiveExperience(lstMonsters.get(countA));
								GiveLoot(lstMonsters.get(countA));
								lstMonsters.get(countA).LOCKDEATH = "yes"; 
								Character_Data.Battle_A = "no";
								Character_Data.Target_A = "none"; 
								Character_Data.Battle_A = "none";
								inBattle = false; 
								lstMonsters.get(countA).BATTLE = "no";
							}
							//lstMonsters.get(countA).HP = String.valueOf(mobHP);
							playerManualAtkDelay = 50;
							return;
				        }
					}
				}
			}
			
			
			if(AutoAttack.equals("no")) { Character_Data.Target_A = "none"; inBattle = false; Character_Data.Battle_A = "none"; playerAttackCooldown = 120; }
			
			if(AutoAttack.equals("yes")) {
				//Verifica se ainda est� em batalha
				if(Character_Data.Target_A.equals("none")) {  //Verifica os monstros na lista pra adicionar o target
					for(countA = 0; countA < lstMonsters.size(); countA++){ 
						mobX = Float.parseFloat(lstMonsters.get(countA).PX) + (Float.parseFloat(lstMonsters.get(countA).WIDTH) / 2);
						mobY = Float.parseFloat(lstMonsters.get(countA).PY) + (Float.parseFloat(lstMonsters.get(countA).HEIGHT) / 2);
						if((mobX > pAttackZoneXMinus && mobX < pAttackZoneXPlus) && (mobY > pAttackZoneYMinus && mobY < pAttackZoneYPlus) ) {				  
							Character_Data.Target_A = lstMonsters.get(countA).ID;
							inBattle  = true;					
							lstMonsters.get(countA).BATTLE = "yes";
							lstMonsters.get(countA).TARGET = Character_Data.Name_A;
							
							//Calcula ATK Cooldown
							playerAttackCooldown = playerAttackCooldown - (playerAgility * 3);  //calcula o cooldown baseado na agi
						}						
					}
				}
				if(!Character_Data.Target_A.equals("none")) {
					playerAttackCooldown--;  //Diminui cooldown 
					
					//Reseta AutoAttack Frame
					if(playerAttackCooldown < -30) { 
						attackFrame = false; 
						playerAttackCooldown = 120; // 120
						playerAttackCooldown = playerAttackCooldown - (playerAgility * 3); 
					}
					
					for(countA = 0; countA < lstMonsters.size(); countA++){ 		  //Verifica Monstro Target dentro da lista			
						if(lstMonsters.get(countA).ID.equals(Character_Data.Target_A)) {
							mobX = Float.parseFloat(lstMonsters.get(countA).PX) + (Float.parseFloat(lstMonsters.get(countA).WIDTH) / 2);
							mobY = Float.parseFloat(lstMonsters.get(countA).PY) + (Float.parseFloat(lstMonsters.get(countA).HEIGHT) / 2);
							if((mobX > pAttackZoneXMinus && mobX < pAttackZoneXPlus) && (mobY > pAttackZoneYMinus && mobY < pAttackZoneYPlus) ) {						
								
								//Posiciona personagem baseado no monstro
								if(pX > mobX) { Character_Data.Battle_A = "yes_Left";}
								if(pX < mobX) { Character_Data.Battle_A = "yes_Right";}
								
								//Liga AutoAttack
								if(playerAttackCooldown < 0) { 
									attackFrame = true; 
								}
								
								//Verifica Classe e atribui o attack
								if(playerAttackCooldown == 0) {
								dmgWeapon = DanoArma();
								
									if(Character_Data.Job_A.equals("Novice")) {
										playerAtk = playerAtk + (playerStrenght * 2);
										mobHP = Integer.parseInt(lstMonsters.get(countA).HP);
										mobDef = Integer.parseInt(lstMonsters.get(countA).DEF);
										monsterEvade = Integer.parseInt(lstMonsters.get(countA).EVADE);							
										mobHP = mobHP - (playerAtk + dmgWeapon);		
										
										Damage danoMob = new Damage();
										danoMob.areaX = Math.round(mobX);
										danoMob.areaY = Math.round(mobY);
										danoMob.dano = String.valueOf(playerAtk);
										danoMob.time = 60;
										danoMob.Color = "Yellow";
										danoMob.Descritivo = "Ataque";
										lstDamage.add(danoMob);
										
										
										if(mobHP <= 0 && lstMonsters.get(countA).LOCKDEATH.equals("no")) { 
											GiveExperience(lstMonsters.get(countA));
											GiveLoot(lstMonsters.get(countA));
											lstMonsters.get(countA).LOCKDEATH = "yes"; 
											Character_Data.Battle_A = "no";
											Character_Data.Target_A = "none"; 
											Character_Data.Battle_A = "none";
											inBattle = false; 
											lstMonsters.get(countA).BATTLE = "no";
										}
										//lstMonsters.get(countA).HP = String.valueOf(mobHP);
										lstMonsters.get(countA).FRAME = "5";
									}
								
									if(Character_Data.Job_A.equals("Swordman")) {
										playerAtk = playerAtk + (playerStrenght * 3) + (playerDextery * 2) + (playerLucky);
										mobHP = Integer.parseInt(lstMonsters.get(countA).HP);
										mobDef = Integer.parseInt(lstMonsters.get(countA).DEF);
										monsterEvade = Integer.parseInt(lstMonsters.get(countA).EVADE);							
										mobHP = mobHP - (playerAtk + dmgWeapon);	
										
									}
									
									if(Character_Data.Job_A.equals("Magician")) {
										playerAtk = playerAtk + (playerMind * 4) + (playerDextery) + (playerLucky);
										mobHP = Integer.parseInt(lstMonsters.get(countA).HP);
										mobDef = Integer.parseInt(lstMonsters.get(countA).DEF);
										monsterEvade = Integer.parseInt(lstMonsters.get(countA).EVADE);							
										mobHP = mobHP - (playerAtk + dmgWeapon);
									}
									
									if(Character_Data.Job_A.equals("Gunner")) {
										playerAtk = playerAtk + (playerDextery * 3) + (playerLucky * 2) + (playerStrenght);
										mobHP = Integer.parseInt(lstMonsters.get(countA).HP);
										mobDef = Integer.parseInt(lstMonsters.get(countA).DEF);
										monsterEvade = Integer.parseInt(lstMonsters.get(countA).EVADE);							
										mobHP = mobHP - (playerAtk + dmgWeapon);
									}
									
									if(Character_Data.Job_A.equals("Thief")) {
										playerAtk = playerAtk + (playerStrenght * 2) + (playerDextery) + (playerLucky * 3);
										mobHP = Integer.parseInt(lstMonsters.get(countA).HP);
										mobDef = Integer.parseInt(lstMonsters.get(countA).DEF);
										monsterEvade = Integer.parseInt(lstMonsters.get(countA).EVADE);							
										mobHP = mobHP - (playerAtk + dmgWeapon);
									}
									
									if(Character_Data.Job_A.equals("Beater")) {
										playerAtk = playerAtk + (playerStrenght * 4) + (playerDextery * 2) + (playerLucky);
										mobHP = Integer.parseInt(lstMonsters.get(countA).HP);
										mobDef = Integer.parseInt(lstMonsters.get(countA).DEF);
										monsterEvade = Integer.parseInt(lstMonsters.get(countA).EVADE);							
										mobHP = mobHP - (playerAtk + dmgWeapon);
									}
									
									if(Character_Data.Job_A.equals("Monk")) {
										playerAtk = playerAtk + (playerStrenght * 2) + (playerDextery) + (playerMind * 2) + (playerAgility);
										mobHP = Integer.parseInt(lstMonsters.get(countA).HP);
										mobDef = Integer.parseInt(lstMonsters.get(countA).DEF);
										monsterEvade = Integer.parseInt(lstMonsters.get(countA).EVADE);							
										mobHP = mobHP - (playerAtk + dmgWeapon);
									}
								}
							}						
						}
					}
				}	
			}
			
			//Para Monstros
			for(countA = 0; countA < lstMonsters.size(); countA++){ 		  //Verifica Monstro Target dentro da lista			
				if(lstMonsters.get(countA).TARGET.equals(Character_Data.Name_A)) {
					
					mobX = Float.parseFloat(lstMonsters.get(countA).PX) + (Float.parseFloat(lstMonsters.get(countA).WIDTH) / 2);
					mobY = Float.parseFloat(lstMonsters.get(countA).PY) + (Float.parseFloat(lstMonsters.get(countA).HEIGHT) / 2);
					
					mobAttackZoneXPlus = mobX + Integer.parseInt(lstMonsters.get(countA).ATKZONEXPLUS);
					mobAttackZoneXMinus = mobX - Integer.parseInt(lstMonsters.get(countA).ATKZONEXMINUS);
					mobAttackZoneYPlus = mobY + Integer.parseInt(lstMonsters.get(countA).ATKZONEYPLUS);
					mobAttackZoneYMinus = mobY - Integer.parseInt(lstMonsters.get(countA).ATKZONEYMINUS);
					
					if((pX > mobAttackZoneXMinus && pX < mobAttackZoneXPlus) && (pY > mobAttackZoneYMinus && pY < mobAttackZoneYPlus) ) {						
								
					mobAttackCooldown = Integer.parseInt(lstMonsters.get(countA).AUTOATK);
					mobAttackCooldown--;
					lstMonsters.get(countA).AUTOATK = String.valueOf(mobAttackCooldown);
					if(mobAttackCooldown == 0) {
						lstMonsters.get(countA).FRAME = "4";
						mobAtk = Integer.parseInt(lstMonsters.get(countA).ATK);
						
						playerHP = Integer.parseInt(Character_Data.HP_A);
						playerHP = playerHP - mobAtk;
						//Character_Data.HP_A = String.valueOf(playerHP);
						
						Damage danoMob = new Damage();
						danoMob.areaX = Math.round(pX);
						danoMob.areaY = Math.round(pY);
						danoMob.dano = String.valueOf(mobAtk);
						danoMob.Color = "Red";
						danoMob.time = 60;
						danoMob.Descritivo = "Ataque";
						lstDamage.add(danoMob);
	     			}
					
					if(mobAttackCooldown < -20) { 
						lstMonsters.get(countA).AUTOATK = lstMonsters.get(countA).AUTOATKBASE; 
						lstMonsters.get(countA).FRAME = "1"; 
					}		
					
					}
				}
			}
		}
		
		public void GiveExperience(Monster mob) {
			
			int exp = Integer.parseInt(mob.EXP);
			int expPlayer = Integer.parseInt(Character_Data.Exp_A);
			int levelPlayer = Integer.parseInt(Character_Data.Level_A);
			int pontosStatus = Integer.parseInt(Character_Data.StatusPoint_A);
			expPlayer = expPlayer + exp;
			
			if(expPlayer >= 100 && levelPlayer == 1) { levelPlayer++; pontosStatus = pontosStatus + 3; expPlayer = 0;}
			if(expPlayer >= 350 && levelPlayer == 2) { levelPlayer++; pontosStatus = pontosStatus + 3; expPlayer = 0;}
			if(expPlayer >= 520 && levelPlayer == 3) { levelPlayer++; pontosStatus = pontosStatus + 3; expPlayer = 0;}
			if(expPlayer >= 780 && levelPlayer == 4) { levelPlayer++; pontosStatus = pontosStatus + 4; expPlayer = 0;}
			if(expPlayer >= 1220 && levelPlayer == 5) { levelPlayer++; pontosStatus = pontosStatus + 4; expPlayer = 0;}
			if(expPlayer >= 2500 && levelPlayer == 6) { levelPlayer++; pontosStatus = pontosStatus + 4; expPlayer = 0;}
			if(expPlayer >= 5600 && levelPlayer == 7) { levelPlayer++; pontosStatus = pontosStatus + 4; expPlayer = 0;}
			if(expPlayer >= 9500 && levelPlayer == 8) { levelPlayer++; pontosStatus = pontosStatus + 4; expPlayer = 0;}
			if(expPlayer >= 15200 && levelPlayer == 9) { levelPlayer++; pontosStatus = pontosStatus + 4; expPlayer = 0;}
			if(expPlayer >= 23400 && levelPlayer == 10) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 26200 && levelPlayer == 11) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 32000 && levelPlayer == 12) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 35000 && levelPlayer == 13) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 39000 && levelPlayer == 14) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 43000 && levelPlayer == 15) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 46400 && levelPlayer == 16) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 52000 && levelPlayer == 17) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 57000 && levelPlayer == 18) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 59000 && levelPlayer == 19) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 65000 && levelPlayer == 20) { levelPlayer++; pontosStatus = pontosStatus + 5; expPlayer = 0;}
			if(expPlayer >= 72000 && levelPlayer == 21) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 74000 && levelPlayer == 22) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 79000 && levelPlayer == 23) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 82000 && levelPlayer == 24) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 85000 && levelPlayer == 25) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 89000 && levelPlayer == 26) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 92340 && levelPlayer == 27) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 97420 && levelPlayer == 28) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 110342 && levelPlayer == 29) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 130020 && levelPlayer == 30) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 150000 && levelPlayer == 31) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 180900 && levelPlayer == 32) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 223100 && levelPlayer == 33) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 255221 && levelPlayer == 34) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 290111 && levelPlayer == 35) { levelPlayer++; pontosStatus = pontosStatus + 6; expPlayer = 0;}
			if(expPlayer >= 339999 && levelPlayer == 36) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			if(expPlayer >= 496554 && levelPlayer == 37) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			if(expPlayer >= 532312 && levelPlayer == 38) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			if(expPlayer >= 699992 && levelPlayer == 39) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			if(expPlayer >= 739231 && levelPlayer == 40) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			if(expPlayer >= 892312 && levelPlayer == 41) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			if(expPlayer >= 1324230 && levelPlayer == 42) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			if(expPlayer >= 1923120 && levelPlayer == 43) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			if(expPlayer >= 3245235 && levelPlayer == 44) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			if(expPlayer >= 5522332 && levelPlayer == 45) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			if(expPlayer >= 8023422 && levelPlayer == 46) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			if(expPlayer >= 11203245 && levelPlayer == 47) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			if(expPlayer >= 19064345 && levelPlayer == 48) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			if(expPlayer >= 36543199 && levelPlayer == 49) { levelPlayer++; pontosStatus = pontosStatus + 7; expPlayer = 0;}
			
			Character_Data.Exp_A = String.valueOf(expPlayer);
			Character_Data.Level_A = String.valueOf(levelPlayer);
			Character_Data.StatusPoint_A = String.valueOf(pontosStatus);
		}
		
		public int VerificaCooldown() {
			return playerAttackCooldown;
		}
		
		public int DanoArma() {
			text = Character_Data.Weapon_A;
			
			if(text.equals("Basic_Sword")) { return 2; }
			if(text.equals("Basic_Rod")) { return 2; }
			if(text.equals("Basic_Knife")) { return 2; }
			if(text.equals("Basic_Axe")) { return 2; }
			if(text.equals("Basic_Pistols")) { return 2; }
			if(text.equals("Basic_Knuckles")) { return 2; }
			
			return 0;
		}
		
		
		// Itens Management //
		public Sprite ItemImage(String item, int pos, float ccX, float ccY){
			
			itemUsage = item.split("#");
			item = itemUsage[0];
			text = item.replace("[","");
			
			if(text.equals("None")) { return spr_master; }
			
			if(text.equals("Cola")) {
				spr_master = atlas_Usable.createSprite("Cola");
			}
			
			qtdItem = itemUsage[1].replace("]","");
			
			if(pos == 0 || pos == 12 || pos == 24 || pos == 36 || pos == 48) { spr_master.setPosition(ccX - 59, ccY - 3); }
			if(pos == 1 || pos == 13 || pos == 25 || pos == 37 || pos == 49) { spr_master.setPosition(ccX - 45, ccY - 3); }
			if(pos == 2 || pos == 14 || pos == 26 || pos == 38 || pos == 50) { spr_master.setPosition(ccX - 31, ccY - 3); }
			if(pos == 3 || pos == 15 || pos == 27 || pos == 39 || pos == 51) { spr_master.setPosition(ccX - 17, ccY - 3); }
			if(pos == 4 || pos == 16 || pos == 28 || pos == 40 || pos == 52) { spr_master.setPosition(ccX - 59, ccY - 29); }
			if(pos == 5 || pos == 17 || pos == 29 || pos == 41 || pos == 53) { spr_master.setPosition(ccX - 45, ccY - 29); }
			if(pos == 6 || pos == 18 || pos == 30 || pos == 42 || pos == 54) { spr_master.setPosition(ccX - 31, ccY - 29); }
			if(pos == 7 || pos == 19 || pos == 31 || pos == 43 || pos == 55) { spr_master.setPosition(ccX - 17, ccY - 29); }
			if(pos == 8 || pos == 20 || pos == 32 || pos == 44 || pos == 56) { spr_master.setPosition(ccX - 59, ccY - 55); }
			if(pos == 9 || pos == 21 || pos == 33 || pos == 45 || pos == 57) { spr_master.setPosition(ccX - 45, ccY - 55); }
			if(pos == 10 || pos == 22 || pos == 34 || pos == 46 || pos == 58) { spr_master.setPosition(ccX - 31, ccY - 55); }
			if(pos == 11 || pos == 23 || pos == 35 || pos == 47 || pos == 59) { spr_master.setPosition(ccX - 17, ccY - 55); }
			spr_master.setSize(15, 30);
			return spr_master;
		}
		
		public Sprite ItemEquipped(String item, float ccX, float ccY) {
			
			String set = Character_Data.Set_A;
			String weapon = Character_Data.Weapon_A;
			String hat = Character_Data.Hat_A;
			
			if(item.equals("weapon")) { 
				if(weapon.equals("basic_knife")) { spr_master = atlas_nKnifes.createSprite("basic_knife_right"); }			
				spr_master.setPosition(ccX - 7, ccY + 26);
				spr_master.setSize(25, 40);
			}
			
			if(item.equals("set")) { 
				if(set.equals("basic_set_male")) { spr_master = atlas_sets.createSprite("basicsetmale"); }
				if(set.equals("basic_set_female")) { spr_master = atlas_sets.createSprite("basicsetfemale"); }
				
				spr_master.setPosition(ccX + 9, ccY + 27);
				spr_master.setSize(20, 30);
			}
			
			if(item.equals("hat")) { 
				if(hat.equals("none")) { return spr_master = null; }
				
			}
			
			
			return spr_master;
		}
		
		public String ItemQuantidade() {	
			return qtdItem;
		}
		
		public String ItemDescritivo(String item){
			if(item.equals("Cola")){ return "Restaura 20 de HP";}
			if(item.equals("SucoHP")){ return "Restaura 90 de HP";}
			if(item.equals("IoguteHP")){ return "Restaura 200 de HP";}
			if(item.equals("RefrigeranteMP")){ return "Restaura 20 de MP";}
			if(item.equals("SucoMP")){ return "Restaura 50 de MP";}
			if(item.equals("IoguteMP")){ return "Restaura 100 de MP";}
			return "";
		}
		
		public void VerificaItemSelecionado(int menuTab, int numItem) {
			
			//[SucoHP#3]-[SucoHP#3]
			
			String[] lstItem = Character_Data.Itens_A.split("-");
			String[] splitItem;
			String nomeItem = "";
			String qtdString = "";
			String tipoItem = "";
			String backItens = "";
			int itemSelecionado = numItem;
			int qtd;
			
			
			//Consolida o item
			if(lstItem[itemSelecionado].equals("[None]")) { return; }
			splitItem = lstItem[itemSelecionado].split("#");
			nomeItem = splitItem[0].replace("[", "");
			qtdString = splitItem[1].replace("]", "");
			qtd = Integer.parseInt(qtdString);
			tipoItem = VerificaTipo(nomeItem);
			if(tipoItem.equals("usable")) { 
				ItemEfeito(nomeItem);
				qtd = qtd -1;
				if(qtd == 0) { 
					lstItem[itemSelecionado] = "[None]"; 
					backItens = Arrays.toString(lstItem).replace(", ","-");
					backItens = backItens.substring(1, backItens.length() -1);
					Character_Data.Itens_A = backItens; 
					return;
				}
				if(qtd > 0) { 
					lstItem[itemSelecionado] = "[" + nomeItem + "#" + qtd + "]"; 
					backItens = Arrays.toString(lstItem).replace(", ","-");
					backItens = backItens.substring(1, backItens.length() -1);
					Character_Data.Itens_A = backItens; 
					return; 
				}						
			}
		}
		
		private String VerificaTipo(String item) {
			
			if(item.equals("Cola")){
				return "usable";
			}
			
			return "";
		}
		
		private void EquipaItem() {
			
		}
		
		private void ItemEfeito(String item){
			//Consumivel
			if(item.equals("Cola")){
				countA = Integer.parseInt(Character_Data.HP_A);
				countA = countA + 20;
				playerHPMAX = Integer.parseInt(Character_Data.HPMAX_A);
				if(countA >= playerHPMAX) { countA = playerHPMAX; } 
				Character_Data.HP_A = String.valueOf(countA);
				WriteDataCharacterActive();
			}
			if(item.equals("Soda")){
				countA = Integer.parseInt(Character_Data.MP_A);
				countA = countA + 50;
				playerMPMAX = Integer.parseInt(Character_Data.MPMAX_A);
				if(countA >= playerMPMAX) { countA = playerMPMAX; } 
				Character_Data.MP_A = String.valueOf(countA);
			}
			if(item.equals("IoguteMP")){
				countA = Integer.parseInt(Character_Data.MP_A);
				countA = countA + 100;
				Character_Data.MP_A = String.valueOf(countA);
			}
		}
		
		private void GiveLoot(Monster mobdeath) {		
			int sortie = 0;		
		}
		
		public void VerificaItemCompra(String nomeloja, int numeroItem) {
			
			int money = Integer.parseInt(Character_Data.Money_A);
			
			
			if(nomeloja.equals("SodaMachine")) {
				if(numeroItem == 1) {
					if(money >= 0) { AdicionaItemMochila("Cola"); money = money;  Character_Data.Money_A = String.valueOf(money); }
				}
			}
		}
		
		public void AdicionaItemMochila(String nomeItem) {
			String[] lstItem = Character_Data.Itens_A.split("-");
			String[] itemSplit;
			boolean exist = false;
			int qtd = 0;
			int posicaoItem = 0;
			String listaItemFinal;
			for(int i = 0; i < lstItem.length; i++) {
				if(lstItem[i].contains(nomeItem) && !exist) {
					posicaoItem = i;
					exist = true;
				}
			}
			
			if(exist) {
			itemSplit = lstItem[posicaoItem].split("#");
			qtd = Integer.parseInt(itemSplit[1].replace("]", ""));
			qtd++;
			if(qtd >= 99) { return;}
			lstItem[posicaoItem] = "[" + itemSplit[0].replace("[", "") + "#" + String.valueOf(qtd) + "]";
			listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
			listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
			Character_Data.Itens_A = listaItemFinal;
			}
			else {
				for(int i = 0; i < lstItem.length; i++) {
					if(lstItem[i].contains("None") && !exist) {
						posicaoItem = i;
						exist = true;
					}
				}
				
				if(exist) {
					lstItem[posicaoItem] = "[" + nomeItem + "#" + "1" + "]";
					listaItemFinal = Arrays.toString(lstItem).replace(", ","-");
					listaItemFinal = listaItemFinal.substring(1, listaItemFinal.length() -1);
					Character_Data.Itens_A = listaItemFinal;
				}
			}
		}
		
		
		// Buffs and Debuffs //
		public void VerificaBuffsDebuffs() {
			
			int timer = 0;		
			playerHP = Integer.parseInt(Character_Data.HP_A);
			playerMP = Integer.parseInt(Character_Data.MP_A);
			playerHPMAX = Integer.parseInt(Character_Data.HPMAX_A);
			playerMPMAX = Integer.parseInt(Character_Data.MPMAX_A);
			
			String[] statusPlayer;
			
			//Verifica Status
			
			
			
			//Equaliza HP
			if(playerHP >= playerHPMAX) { playerHP = playerHPMAX; }
			if(playerMP >= playerMPMAX) { playerMP = playerMPMAX; }
			Character_Data.HP_A = String.valueOf(playerHP);
			Character_Data.MP_A = String.valueOf(playerMP);
		}
		
		public ArrayList<Buffs> RetornaBuffs() {
			return lstBuffs;
		}
		
		//Regenera HP
		public void RegenerateHPTiming() {
			playerHP = Integer.parseInt(Character_Data.HP_A);
			playerMP = Integer.parseInt(Character_Data.MP_A);
			playerHPMAX = Integer.parseInt(Character_Data.HPMAX_A);
			playerMPMAX = Integer.parseInt(Character_Data.MPMAX_A);
						
			if(playerHP <= playerHPMAX) { playerHP = playerHP + 20; }		
			if(playerMP <= playerMPMAX) { playerMP = playerMP + 10; }
				
			if(playerHP >= playerHPMAX) { playerHP = playerHPMAX; }
			if(playerMP >= playerMPMAX) { playerMP = playerMPMAX; }
						
			if(playerHP < 0) { playerHP = 0;}
			if(playerMP < 0) { playerMP = 0;}
			
			Character_Data.HP_A = String.valueOf(playerHP);
			Character_Data.MP_A = String.valueOf(playerMP);
		}
		
		
		
		// NPCS and Quests //
		
		public int IniciaQuest(String nomeQuest) {
			
			
			return 1;
		}
		
		public String ConfiguraQuest(String nomeQuest, int etapaTexto) {
			
			if(!Character_Data.Quests_A.contains("Um pedido gentil")) {
				Character_Data.Quests_A = "Um pedido gentil";
			
				if(nomeQuest.equals("Um pedido gentil") && etapaTexto == 1) {
					return "Bom dia,  estou vendo que voce e novo por aqui...";
				}
				if(nomeQuest.equals("Um pedido gentil") && etapaTexto == 2) {
					return "desculpe-me, voce parece um pouco surpreso de estar aqui hihi";
				}
				if(nomeQuest.equals("Um pedido gentil") && etapaTexto == 3) {
					return "nao sei de onde voce veio, mas parece ser de longe";
				}
				if(nomeQuest.equals("Um pedido gentil") && etapaTexto == 4) {
					return "sabe, eu estou meio encrencada. Minha mae me pediu para comprar Mel, mas todas as lojas onde procurei esta em falta";
				}
				if(nomeQuest.equals("Um pedido gentil") && etapaTexto == 5) {
					return "ouvi algumas pessoas falando que voce pode encontrar 'Mel' diretamente das abelhas";
				}
				if(nomeQuest.equals("Um pedido gentil") && etapaTexto == 6) {
					return "se voce encontrar alguma pelo seu caminho poderia trazer um pouco pra min?";
				}
				if(nomeQuest.equals("Um pedido gentil") && etapaTexto == 7) {
					return "ohh...  nossa...  muito obrigada!";
				}
			}
			
			return "";
		}
		
		public String NomeNPCTexto(String nomeQuest, int etapaTexto) {
			
			if(nomeQuest.equals("Um pedido gentil") && etapaTexto == 1) {
				return "Rina";
			}
			
			return "";
		}
		
		public ArrayList<Sprite> ExibeNPCs(String map){
		
		   lstSprites.clear();
		   npcframe++;
		   npcframe2++;
		   npcframewalk++;
		   endright = endright - 0.4f;
		   endleft = endleft + 0.4f;
		   
		   if(npcframe > 300){
			   npcframe = 1;
		   }
		   if(npcframe2 > 120){
				npcframe2 = 1;
			}
		   
		   if(map.equals("MetroStation")){	   
			   if(endright < - 50){ endright = 300; }
			   if(endleft > 300){ endleft = -50; }
		   }
		   
			if(map.equals("MetroStation")){
			   //Guarda
			   if(npcframe >= 1 && npcframe <= 100){
			        spr_master = atlas_Npcs.createSprite("guard1");   
			        spr_master.setPosition(100, -15);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
			   }
			   if(npcframe >= 100 && npcframe <= 200){
					spr_master = atlas_Npcs.createSprite("guard2");   
					spr_master.setPosition(100, -15);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
			   }
			   if(npcframe >= 200 && npcframe <= 300){
					spr_master = atlas_Npcs.createSprite("guard3");   
					spr_master.setPosition(100, -15);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
			   }
				//lady 
				if(npcframe2 >= 1 && npcframe2 <= 40){
			        spr_master = atlas_Npcs.createSprite("lady1");   
			        spr_master.setPosition(50, -23);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 80){
					spr_master = atlas_Npcs.createSprite("lady2");   
					spr_master.setPosition(50, -23);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("lady3");   
					spr_master.setPosition(50, -23);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//cooker
				if(npcframe2 >= 1 && npcframe2 <= 40){
			        spr_master = atlas_Npcs.createSprite("cooker1");   
			        spr_master.setPosition(170, -25);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 80){
					spr_master = atlas_Npcs.createSprite("cooker2");   
					spr_master.setPosition(170, -25);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("cooker3");   
					spr_master.setPosition(170, -25);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//worker1
				if(npcframe2 >= 1 && npcframe2 <= 19){
			        spr_master = atlas_Npcs.createSprite("worker1");   
			        spr_master.setPosition(endleft, -55);
			        spr_master.setSize(16, 39);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 20 && npcframe2 <= 39){
					spr_master = atlas_Npcs.createSprite("worker2");   
					spr_master.setPosition(endleft, -55);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 59){
					spr_master = atlas_Npcs.createSprite("worker1");   
					spr_master.setPosition(endleft, -55);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 60 && npcframe2 <= 79){
			        spr_master = atlas_Npcs.createSprite("worker3");   
			        spr_master.setPosition(endleft, -55);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 99){
					spr_master = atlas_Npcs.createSprite("worker1");   
					spr_master.setPosition(endleft, -55);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 100 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("worker2");   
					spr_master.setPosition(endleft, -55);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//walker1
				if(npcframe2 >= 1 && npcframe2 <= 19){
			        spr_master = atlas_Npcs.createSprite("walker1");   
			        spr_master.setPosition(endleft - 105, -60);
			        spr_master.setSize(16, 39);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 20 && npcframe2 <= 39){
					spr_master = atlas_Npcs.createSprite("walker2");   
					spr_master.setPosition(endleft - 105, -60);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 59){
					spr_master = atlas_Npcs.createSprite("walker1");   
					spr_master.setPosition(endleft - 105, -60);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 60 && npcframe2 <= 79){
			        spr_master = atlas_Npcs.createSprite("walker3");   
			        spr_master.setPosition(endleft - 105, -60);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 99){
					spr_master = atlas_Npcs.createSprite("walker1");   
					spr_master.setPosition(endleft - 105, -60);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 100 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("walker2");   
					spr_master.setPosition(endleft - 105, -60);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				
				//SchoolerD
				if(npcframe2 >= 1 && npcframe2 <= 19){
			        spr_master = atlas_Npcs.createSprite("schoolerD2");   
			        spr_master.setPosition(endright, -75);
			        spr_master.setSize(16, 39);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 20 && npcframe2 <= 39){
					spr_master = atlas_Npcs.createSprite("schoolerD1");   
					spr_master.setPosition(endright, -75);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 59){
					spr_master = atlas_Npcs.createSprite("schoolerD2");   
					spr_master.setPosition(endright, -75);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 60 && npcframe2 <= 79){
			        spr_master = atlas_Npcs.createSprite("schoolerD3");   
			        spr_master.setPosition(endright, -75);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 99){
					spr_master = atlas_Npcs.createSprite("schoolerD2");   
					spr_master.setPosition(endright, -75);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 100 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("schoolerD1");   
					spr_master.setPosition(endright, -75);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//SchoolerC
				if(npcframe2 >= 1 && npcframe2 <= 19){
			        spr_master = atlas_Npcs.createSprite("schoolerC2");   
			        spr_master.setPosition(endright + 70, -30);
			        spr_master.setSize(16, 39);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 20 && npcframe2 <= 39){
					spr_master = atlas_Npcs.createSprite("schoolerC1");   
					spr_master.setPosition(endright + 70, -30);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 59){
					spr_master = atlas_Npcs.createSprite("schoolerC2");   
					spr_master.setPosition(endright + 70, -30);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 60 && npcframe2 <= 79){
			        spr_master = atlas_Npcs.createSprite("schoolerC3");   
			        spr_master.setPosition(endright + 70, -30);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 99){
					spr_master = atlas_Npcs.createSprite("schoolerC2");   
					spr_master.setPosition(endright + 70, -30);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 100 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("schoolerC1");   
					spr_master.setPosition(endright + 70, -30);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
			}
			
			
			if(map.equals("Streets305")){
				//SchoolerB
				if(npcframe2 >= 1 && npcframe2 <= 40){
			        spr_master = atlas_Npcs.createSprite("schoolerB1");   
			        spr_master.setPosition(209, -152);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 80){
					spr_master = atlas_Npcs.createSprite("schoolerB1");   
					spr_master.setPosition(209, -152);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("schoolerB2");   
					spr_master.setPosition(209, -152);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//Cristal guy
				if(npcframe2 >= 1 && npcframe2 <= 40){
			        spr_master = atlas_Npcs.createSprite("crystalguy1");   
			        spr_master.setPosition(349, -80);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 80){
					spr_master = atlas_Npcs.createSprite("crystalguy2");   
					spr_master.setPosition(349, -80);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("crystalguy3");   
					spr_master.setPosition(349, -80);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//Flower Girl
				if(npcframe2 >= 1 && npcframe2 <= 40){
			        spr_master = atlas_Npcs.createSprite("flowergirl1");   
			        spr_master.setPosition(62, -80);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 80){
					spr_master = atlas_Npcs.createSprite("flowergirl2");   
					spr_master.setPosition(62, -80);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("flowergirl3");   
					spr_master.setPosition(62, -80);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				
				//SchoolerA
				if(npcframe2 >= 1 && npcframe2 <= 19){
			        spr_master = atlas_Npcs.createSprite("schoolerA2");   
			        spr_master.setPosition(endleft, -131);
			        spr_master.setSize(16, 39);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 20 && npcframe2 <= 39){
					spr_master = atlas_Npcs.createSprite("schoolerA1");   
					spr_master.setPosition(endleft, -131);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 40 && npcframe2 <= 59){
					spr_master = atlas_Npcs.createSprite("schoolerA2");   
					spr_master.setPosition(endleft, -131);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 60 && npcframe2 <= 79){
			        spr_master = atlas_Npcs.createSprite("schoolerA3");   
			        spr_master.setPosition(endleft, -131);
			        spr_master.setSize(16, 40);
			        lstSprites.add(spr_master);
				}
				if(npcframe2 >= 80 && npcframe2 <= 99){
					spr_master = atlas_Npcs.createSprite("schoolerA2");   
					spr_master.setPosition(endleft, -131);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
				if(npcframe2 >= 100 && npcframe2 <= 120){
					spr_master = atlas_Npcs.createSprite("schoolerA1");   
					spr_master.setPosition(endleft, -131);
					spr_master.setSize(16, 40);
					lstSprites.add(spr_master);
				}
			}
			
			return lstSprites;
		}
}