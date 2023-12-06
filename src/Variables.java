import com.sun.opengl.util.FPSAnimator;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("ClassEscapesDefinedScope")
public class Variables {
    public static File file = new File("src/qaof9-5iz1b.wav");
    public static AudioInputStream audioStream;

    static {
        try {
            audioStream = AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Clip clip;

    static {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
    public static long clipTime;
    public static FPSAnimator menu_Animator = new FPSAnimator(60);
    public static int current_Maze = 0;
    public static String[][] Maze1;
    public static String[][] Maze2;
    public static String[][] Maze3;
    public static String[][] Maze4;
    public static String[][] Maze5;
    public static String[][] Maze6;
    public static String[][] Maze7;
    public static String[][] Maze8;
    public static String[][] Maze9;
    public static List<List<String[][]>> Mazes = new ArrayList<>();
    public static FPSAnimator game_Animator = new FPSAnimator(60);
    public static String[][] player1_Maze_copy;
    public static String[][] player2_Maze_copy;
    public static String[][] AI_Maze_copy;
    public static int levelSelection = -1;
    public static boolean selected_Mode = false;
    public static int Num_Players = 0;
    public static String player1;
    public static String player2;
    public static Cell x_Player1 = new Cell(1,1);
    public static Cell x_Player2 = new Cell(1,1);
    public static booleanVal player1_Collided = new booleanVal(false);
    public static booleanVal player2_Collided = new booleanVal(false);
    public static booleanVal player1_Win = new booleanVal(false);
    public static booleanVal player2_Win = new booleanVal(false);
    public static int player1_Lives = 0;
    public static int player2_Lives = 0;
    public static int player1_HighScore = 0;
    public static int player2_HighScore = 0;
    public static boolean selectedAI = false;
    public static boolean AI_Moved = false;
    public static boolean AI_Won = false;
    public static int x_Random = 0;
    public static int y_Random = 0;
    public static boolean generated_Trap = false;
    public static boolean muted = false;
    public static boolean stop = false;
    public static int seconds = 0;
    public static int minutes = 0;
    public static int max_Minutes = 0;
    public static boolean menu_disabled = false;
}
class booleanVal{
    boolean exp;
    booleanVal(boolean exp){
        this.exp = exp;
    }
}
