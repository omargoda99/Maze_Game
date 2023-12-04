import com.sun.opengl.util.FPSAnimator;

import java.util.ArrayList;
import java.util.List;

public class Variables {
    static int current_Maze = 0;
    static String[][] Maze1;
    static String[][] Maze2;
    static String[][] Maze3;
    static List<String[][]> Mazes = new ArrayList<>();

    static String[][] player1_Maze_copy;
    static String[][] player2_Maze_copy;
    static String[][] AI_Maze_copy;
    static int levelSelection = 1;
    static int Num_Players = 1;
    static String player1;
    static String player2;
    FPSAnimator animator = new FPSAnimator(25);
    static int x_Player1 = 1;
    static int y_Player1 = 1;
    static int x_Player2 = 1;
    static int y_Player2 = 1;
    boolean player1_Collided = false;
    boolean player2_Collided = false;
    static int player1_Lives = 7;
    static int player2_Lives = 7;
    static boolean player1_Win = false;
    static boolean player2_Win = false;
    static int player1_Score = 0;
    static int player2_Score = 0;
    static boolean selectedAI = false;
    static boolean AI_Moved = false;
    static boolean AI_Won = false;
    static int x_Random = 0;
    static int y_Random = 0;
    static boolean generated_Trap = false;
    static boolean muted = false;
    static boolean stop = false;
    static int seconds = 0;
    static int minutes = 0;
}
