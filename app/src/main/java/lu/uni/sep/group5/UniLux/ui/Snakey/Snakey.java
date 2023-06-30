package lu.uni.sep.group5.UniLux.ui.Snakey;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class Snakey {


    private int SizeOfSnakey;
    private int[] snakeXposition;
    private int[] snakeYposition;
    private int individualBlocksize;

    public  enum Direction {UP,RIGHT,DOWN,LEFT}
    private Direction  direction = Direction.RIGHT;

    private int HorizontalNumbOfBlocks;
    private int VerticalNumofBlocks;


    public Snakey(int SizeOfSnakey, int[] snakeXposition, int[] snakeYposition, int individualBlocksize, int HorizontalNumbOfBlocks, int VerticalNumofBlocks){

        this.SizeOfSnakey = SizeOfSnakey;
        this.snakeXposition = snakeXposition;
        this.snakeYposition = snakeYposition;
        this.individualBlocksize =individualBlocksize;
        this.HorizontalNumbOfBlocks = HorizontalNumbOfBlocks;
        this.VerticalNumofBlocks = VerticalNumofBlocks;

    }


    public void draw(Canvas canvas, Paint paint){

        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);



        paint.setColor(Color.argb(255, r, g, b));
        for (int i = 0; i < SizeOfSnakey; i++) {

            canvas.drawRect(snakeXposition[i] * individualBlocksize,
                    (snakeYposition[i] * individualBlocksize),
                    (snakeXposition[i] * individualBlocksize) + individualBlocksize,
                    (snakeYposition[i] * individualBlocksize) + individualBlocksize,
                    paint);
        }

    }

    public boolean death(){
        boolean dead = false;

        if (snakeXposition[0] == -1) dead = true;
        if (snakeXposition[0] >= HorizontalNumbOfBlocks) dead = true;
        if (snakeYposition[0] == -1) dead = true;
        if (snakeYposition[0] == VerticalNumofBlocks) dead = true;

        for (int i = SizeOfSnakey - 1; i > 0; i--) {
            if ((i > 4) && (snakeXposition[0] == snakeXposition[i]) && (snakeYposition[0] == snakeYposition[i])) {
                dead = true;
            }
        }

        return dead;
    }



}





