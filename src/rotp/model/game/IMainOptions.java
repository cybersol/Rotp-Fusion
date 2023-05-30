package rotp.model.game;

import static rotp.ui.util.InterfaceParam.langLabel;

import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.JFileChooser;

import rotp.Rotp;
import rotp.ui.UserPreferences;
import rotp.ui.game.BaseModPanel;
import rotp.ui.util.InterfaceParam;
import rotp.ui.util.ParamBoolInt;
import rotp.ui.util.ParamBoolean;
import rotp.ui.util.ParamInteger;
import rotp.ui.util.ParamList;
import rotp.ui.util.ParamString;
import rotp.util.sound.SoundManager;

public interface IMainOptions extends IBaseOptsTools {
	String WINDOW_MODE			= "GAME_SETTINGS_WINDOWED";
	String BORDERLESS_MODE		= "GAME_SETTINGS_BORDERLESS";
	String FULLSCREEN_MODE		= "GAME_SETTINGS_FULLSCREEN";
	String GRAPHICS_LOW			= "GAME_SETTINGS_GRAPHICS_LOW";
	String GRAPHICS_MEDIUM		= "GAME_SETTINGS_GRAPHICS_MED";
	String GRAPHICS_HIGH		= "GAME_SETTINGS_GRAPHICS_HIGH";
	String AUTOBOMBARD_NO		= "GAME_SETTINGS_AUTOBOMBARD_NO";
	String AUTOBOMBARD_NEVER	= "GAME_SETTINGS_AUTOBOMBARD_NEVER";
	String AUTOBOMBARD_YES		= "GAME_SETTINGS_AUTOBOMBARD_YES";
	String AUTOBOMBARD_WAR		= "GAME_SETTINGS_AUTOBOMBARD_WAR";
	String AUTOBOMBARD_INVADE	= "GAME_SETTINGS_AUTOBOMBARD_INVADE";
	String TEXTURES_NO			= "GAME_SETTINGS_TEXTURES_NO";
	String TEXTURES_INTERFACE	= "GAME_SETTINGS_TEXTURES_INTERFACE";
	String TEXTURES_MAP			= "GAME_SETTINGS_TEXTURES_MAP";
	String TEXTURES_BOTH		= "GAME_SETTINGS_TEXTURES_BOTH";
	String SAVEDIR_DEFAULT		= "GAME_SETTINGS_SAVEDIR_DEFAULT";
	String SAVEDIR_CUSTOM		= "GAME_SETTINGS_SAVEDIR_CUSTOM";
	String SENSITIVITY_HIGH		= "GAME_SETTINGS_SENSITIVITY_HIGH";
	String SENSITIVITY_MEDIUM	= "GAME_SETTINGS_SENSITIVITY_MEDIUM";
	String SENSITIVITY_LOW		= "GAME_SETTINGS_SENSITIVITY_LOW";
	int	   MAX_BACKUP_TURNS		= 20; // modnar: change max turns between backups to 20
	
	// ==================== Duplicates for Base Main Settings Options ====================
	//
	ParamList displayMode		= new ParamList( GAME_UI, "DISPLAY_MODE",
			Arrays.asList(
					WINDOW_MODE,
					BORDERLESS_MODE,
					FULLSCREEN_MODE
					),
			WINDOW_MODE) {
		{
			isDuplicate(true);
			isCfgFile(true);
			showFullGuide(true);
		}
		@Override public String getOptionValue(IGameOptions options) {
			return UserPreferences.displayMode();
		}
		@Override public void setOption(String newValue) {
			UserPreferences.displayMode(newValue);
			UserPreferences.save();
			return;
		}
	};
//	default boolean fullScreen()	{ return displayMode().equals(FULLSCREEN_MODE); }
//	default boolean windowed()		{ return displayMode().equals(WINDOW_MODE); }
//	default boolean borderless()	{ return displayMode().equals(BORDERLESS_MODE); }
//	default String displayMode()	{ return displayMode.get(); }
	
	ParamList graphicsMode		= new ParamList( GAME_UI, "GRAPHICS",
			Arrays.asList(
					GRAPHICS_HIGH,
					GRAPHICS_MEDIUM,
					GRAPHICS_LOW
					),
			GRAPHICS_HIGH) {
		{
			isDuplicate(true);
			isCfgFile(true);
			showFullGuide(true);
		}
		@Override public String getOptionValue(IGameOptions options) {
			return UserPreferences.graphicsMode();
		}
		@Override public void setOption(String newValue) {
			UserPreferences.graphicsMode(newValue);
			UserPreferences.save();
			return;
		}
	};
//	default boolean playAnimations()	{ return !graphicsMode.get().equals(GRAPHICS_LOW); }
//	default boolean antialiasing()		{ return graphicsMode.get().equals(GRAPHICS_HIGH); }

	ParamList texturesMode		= new ParamList( GAME_UI, "GRAPHICS",
			Arrays.asList(
					TEXTURES_NO,
					TEXTURES_INTERFACE,
					TEXTURES_MAP,
					TEXTURES_BOTH
					),
			TEXTURES_BOTH) {
		{
			isDuplicate(true);
			isCfgFile(true);
			showFullGuide(true);
		}
		@Override public String getOptionValue(IGameOptions options) {
			return UserPreferences.texturesMode();
		}
		@Override public void setOption(String newValue) {
			UserPreferences.texturesMode(newValue);
			UserPreferences.save();
			return;
		}
	};
//	default boolean texturesInterface()		{ return UserPreferences.texturesInterface(); }
//	default boolean texturesMap()			{ return UserPreferences.texturesMap(); }

	ParamList sensitivityMode		= new ParamList( GAME_UI, "GRAPHICS",
			Arrays.asList(
					SENSITIVITY_LOW,
					SENSITIVITY_MEDIUM,
					SENSITIVITY_HIGH
					),
			SENSITIVITY_MEDIUM) {
		{
			isDuplicate(true);
			isCfgFile(true);
			showFullGuide(true);
		}
		@Override public String getOptionValue(IGameOptions options) {
			return UserPreferences.sensitivityMode();
		}
		@Override public void setOption(String newValue) {
			UserPreferences.sensitivityMode(newValue);
			UserPreferences.save();
			return;
		}
	};
//	default boolean sensitivityMedium()		{ return UserPreferences.sensitivityMedium(); }
//	default boolean sensitivityLow()		{ return UserPreferences.sensitivityLow(); }

	// Do no use alone, Go through soundVolume
	ParamBoolean playSounds		= new ParamBoolean(GAME_UI, "SOUNDS", true) {
		{
			isDuplicate(true);
			isCfgFile(true);
		}
		@Override public Boolean getOptionValue(IGameOptions options) {
			return UserPreferences.playSounds();
		}
		@Override public void setOption(Boolean newValue) {
			UserPreferences.playSounds(!newValue); // ! because following line will toggle!
			SoundManager.current().toggleSounds();
			UserPreferences.save();
			return;
		}
	};
	ParamBoolInt soundVolume	= new ParamBoolInt(GAME_UI, playSounds,
			"SOUNDS_ON", "SOUNDS_OFF", 10, 0, 10) {
		{
			isDuplicate(true);
			isCfgFile(true);
		}
		@Override public Integer getOptionValue(IGameOptions options) {
			return UserPreferences.soundVolume();
		}
		@Override public void setOption(Integer newValue) {
			UserPreferences.soundVolume(Math.abs(newValue));
			SoundManager.current().resetSoundVolumes();
			UserPreferences.save();
			return;
		}
	};
//	default int soundVolume()		{ return soundVolume.get(); }
//	default boolean playSounds()	{ return playSounds.get(); }
	
	// Do no use alone, Go through musicVolume
	ParamBoolean playMusic		= new ParamBoolean(GAME_UI, "MUSIC", true) {
		{
			isDuplicate(true);
			isCfgFile(true);
		}
		@Override public Boolean getOptionValue(IGameOptions options) {
			return UserPreferences.playMusic();
		}
		@Override public void setOption(Boolean newValue) {
			UserPreferences.playMusic(!newValue); // ! because following line will toggle!
			SoundManager.current().toggleMusic();
			UserPreferences.save();
			return;
		}
	};
	ParamBoolInt musicVolume	= new ParamBoolInt(GAME_UI, playMusic,
			"MUSIC_ON", "MUSIC_OFF", 10, 0, 10) {
		{
			isDuplicate(true);
			isCfgFile(true);
		}
		@Override public Integer getOptionValue(IGameOptions options) {
			return UserPreferences.musicVolume();
		}
		@Override public void setOption(Integer newValue) {
			UserPreferences.musicVolume(Math.abs(newValue));
			SoundManager.current().resetMusicVolumes();
			UserPreferences.save();
			return;
		}
	};
//	default int musicVolume()		{ return musicVolume.get(); }
//	default boolean playMusic()		{ return playMusic.get(); }

	ParamBoolean showMemory		= new ParamBoolean(GAME_UI, "MEMORY", true) {
		{
			isDuplicate(true);
			isCfgFile(true);
		}
		@Override public String getCfgLabel() { return GAME_UI + "SHOW_MEMORY"; }
		@Override public Boolean getOptionValue(IGameOptions options) {
			return UserPreferences.showMemory();
		}
		@Override public void setOption(Boolean newValue) {
			UserPreferences.showMemory(newValue);
			UserPreferences.save();
			return;
		}
	};
//	default boolean showMemory()		{ return showMemory.get(); }

	ParamInteger selectedScreen		= new ParamInteger(GAME_UI, "SELECTED_SCREEN", -1, -1, Rotp.maxScreen()) {
		{
			isDuplicate(true);
			isCfgFile(true);
		}
		@Override public Integer getOptionValue(IGameOptions options) {
			return UserPreferences.selectedScreen();
		}
		@Override public void setOption(Integer newValue) {
			UserPreferences.selectedScreen(newValue);
			UserPreferences.save();
			return;
		}
	};
//	default int selectedScreen()		{ return selectedScreen.get(); }

	ParamBoolean autoColonize_	= new ParamBoolean( GAME_UI, "AUTOCOLONIZE", false) {
		{
			isDuplicate(true);
			isCfgFile(true);
		}
		@Override public Boolean getOptionValue(IGameOptions options) {
			return UserPreferences.autoColonize();
		}
		@Override public void setOption(Boolean newValue) {
			UserPreferences.autoColonize(newValue);
			UserPreferences.save();
		}
	};
	default boolean autoColonizeMode()		{ return autoColonize_.get(); }
//	default void autoColonize()	{ autoColonize_.get();  }

	ParamList autoBombard_		= new ParamList( GAME_UI, "AUTOBOMBARD",
			Arrays.asList(
					AUTOBOMBARD_NO,
					AUTOBOMBARD_NEVER,
					AUTOBOMBARD_YES,
					AUTOBOMBARD_WAR,
					AUTOBOMBARD_INVADE
					),
			AUTOBOMBARD_NO) {
		{
			isDuplicate(true);
			isCfgFile(true);
			showFullGuide(true);
		}
		@Override public String getOptionValue(IGameOptions options) {
			return UserPreferences.autoBombardMode();
		}
		@Override public void setOption(String newValue) {
			UserPreferences.autoBombardMode(newValue);
			UserPreferences.save();
			return;
		}
	};
//	default String  autoBombardMode()		{ return autoBombard_.get(); }
//	default boolean autoBombardNever()		{ return autoBombard_.get().equals(AUTOBOMBARD_NEVER); }
//	default boolean autoBombardYes()		{ return autoBombard_.get().equals(AUTOBOMBARD_YES); }
//	default boolean autoBombardWar()		{ return autoBombard_.get().equals(AUTOBOMBARD_WAR); }
//	default boolean autoBombardInvading()	{ return autoBombard_.get().equals(AUTOBOMBARD_INVADE); }

	ParamInteger backupTurns	= new ParamInteger(GAME_UI, "BACKUP", 5, 0, MAX_BACKUP_TURNS, 1, 5, 10) {
		{
			isDuplicate(true);
			isCfgFile(true);
			specialZero(GAME_UI + "BACKUP_OFF");
		}
		@Override public String getCfgLabel() { return GAME_UI + "BACKUP_TURNS"; }
		@Override public Integer getOptionValue(IGameOptions options) {
			return UserPreferences.backupTurns();
		}
		@Override public void setOption(Integer newValue) {
			UserPreferences.backupTurns(newValue);
			//UserPreferences.save(); already done in the up call
			return;
		}
	};
//	default int backupTurns()		{ return backupTurns.get(); }

	ParamString saveDirectory	= new ParamString(GAME_UI, "SAVEDIR", "") {
		{
			isDuplicate(true);
			isCfgFile(true);
		}
		@Override protected String descriptionId()	{
			String es = get().isEmpty()? "1" : "2";
			String label = super.descriptionId() + es;
			return label;			
		}
		@Override public String getGuiDescription() {
			return langLabel(descriptionId(), get());
		}
		@Override public String guideValue() {
			String es = get().isEmpty()? "_DEFAULT" : "_CUSTOM";
			String label = getLangLabel() + es;
			return langLabel(label);
		}
		@Override public void toggle(MouseEvent e, BaseModPanel frame)	{
			if (getDir(e) == 0) {
				set("");
				return;
			}
	        final JFileChooser fc = new JFileChooser();
	        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	        File saveDir = new File(UserPreferences.saveDirectoryPath());
	        fc.setCurrentDirectory(saveDir);
	        int returnVal = fc.showOpenDialog(frame);
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            String path = fc.getSelectedFile().getAbsolutePath();
	            set(path);
	        }
	    }
		@Override public String getCfgLabel() { return GAME_UI + "SAVE_DIR"; }
		@Override public String getOptionValue(IGameOptions options) {
			return UserPreferences.saveDir();
		}
		@Override public void setOption(String newValue) {
			UserPreferences.saveDir(newValue);
			//UserPreferences.save(); already done in the up call
			return;
		}
	};
//	default int saveDir()		{ return saveDirectory.get(); }


	// ==================== GUI List Declarations ====================
	//
	LinkedList<InterfaceParam> mainOptions	  = new LinkedList<>(
			Arrays.asList(
					displayMode, graphicsMode, texturesMode, sensitivityMode,
					null,
					soundVolume, musicVolume, showMemory, selectedScreen,
					null,
					autoColonize_, autoBombard_, backupTurns, saveDirectory
					// displayYear
					));

}