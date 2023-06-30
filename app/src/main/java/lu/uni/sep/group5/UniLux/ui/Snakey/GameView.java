package lu.uni.sep.group5.UniLux.ui.Snakey;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class GameView extends SurfaceView implements Runnable {


    private Thread thread = null;

    public enum Direction {UP, RIGHT, DOWN, LEFT}
    private Direction direction = Direction.RIGHT;

    private int scrX;
    private int scrY;

    private int Sizeofsnakey;

    private int foodX;
    private int foodY;

    private int individualBlocksize;

    private final int HorizontalNumbOfBlocks = 40;
    private int VerticalNumofBlocks;

    private long nxtFrameT;
    private final long framePerSecond = 10;
    private final long mPerSecond = 1000;

    private int score;

    private int[] snakeXposition;
    private int[] snakeYposition;

    private volatile boolean Running;

    private Canvas canvas;

    private SurfaceHolder surfaceHolder;

    private Paint paint;

    Snakey snakey;

    public GameView(Context context, Point size) {
        super(context);

        scrX = size.x;
        scrY = size.y;

        individualBlocksize = scrX / HorizontalNumbOfBlocks;
        VerticalNumofBlocks = scrY / individualBlocksize;

        surfaceHolder = getHolder();
        paint = new Paint();

        snakeXposition = new int[200];
        snakeYposition = new int[200];

        newGame();
    }

    @Override
    public void run() {

        while (Running) {
            snakey = new Snakey(Sizeofsnakey, snakeXposition,snakeYposition,individualBlocksize,HorizontalNumbOfBlocks,VerticalNumofBlocks);

            if(updateRequired()) {
                update();
                draw();
            }

        }
    }

    public void pause() {
        Running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        Running = true;
        thread = new Thread(this);
        thread.start();
    }


    public void newGame() {
        Sizeofsnakey = 1;
        snakeXposition[0] = HorizontalNumbOfBlocks / 2;
        snakeYposition[0] = VerticalNumofBlocks / 2;

        randomizeFood();

        score = 0;

        nxtFrameT = System.currentTimeMillis();
    }

    public void randomizeFood() {
        Random random = new Random();
        foodX = random.nextInt(HorizontalNumbOfBlocks - 1) + 1;
        foodY = random.nextInt(VerticalNumofBlocks - 1) + 1;
    }


    private void consumeFood(){
        Sizeofsnakey+=2;
        randomizeFood();
        score = score + 1;
    }


    private void snakeyMoves(){
        for (int i = Sizeofsnakey; i > 0; i--) {

            snakeXposition[i] = snakeXposition[i - 1];
            snakeYposition[i] = snakeYposition[i - 1];

        }

        switch (direction) {
            case UP:
                snakeYposition[0]--;
                break;

            case RIGHT:
                snakeXposition[0]++;
                break;

            case DOWN:
                snakeYposition[0]++;
                break;

            case LEFT:
                snakeXposition[0]--;
                break;
        }
    }

    public void update() {
        if (snakeXposition[0] == foodX && snakeYposition[0] == foodY) {
            consumeFood();
        }
        snakey = new Snakey(Sizeofsnakey, snakeXposition,snakeYposition,individualBlocksize,HorizontalNumbOfBlocks,VerticalNumofBlocks);
        snakeyMoves();
        if (snakey.death()) {
            newGame();
        }
    }

    public void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.argb(255, 204, 153, 255));

            paint.setColor(Color.argb(255, 255, 255, 255));

            Random rand = new Random();
            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);


            paint.setTextAlign(Paint.Align.LEFT);
            paint.setColor(Color.argb(255,r,255,255));
            paint.setTypeface(Typeface.create("Arial", Typeface.ITALIC));
            paint.setTextSize(50);
            canvas.drawText("Score:" + score, 55, 90, paint);

            snakey = new Snakey(Sizeofsnakey, snakeXposition,snakeYposition,individualBlocksize,HorizontalNumbOfBlocks,VerticalNumofBlocks);
            snakey.draw(canvas, paint);

            paint.setColor(Color.WHITE);
            canvas.drawRect(foodX * individualBlocksize, (foodY * individualBlocksize), (foodX * individualBlocksize) + individualBlocksize, (foodY * individualBlocksize) + individualBlocksize, paint);


            Paint p = new Paint();
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(6);
            p.setColor(Color.WHITE);
            canvas.drawRect(30,   40 , 250,120,p);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public boolean updateRequired() {


        if(nxtFrameT <= System.currentTimeMillis()){

            nxtFrameT =System.currentTimeMillis() + mPerSecond / framePerSecond;


            return true;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (motionEvent.getX() >= scrX / 2) {
                    switch(direction){
                        case UP:
                            direction = Direction.RIGHT;
                            break;
                        case RIGHT:
                            direction = Direction.DOWN;
                            break;
                        case DOWN:
                            direction = Direction.LEFT;
                            break;
                        case LEFT:
                            direction = Direction.UP;
                            break;
                    }
                } else {
                    switch(direction){
                        case UP:
                            direction = Direction.LEFT;
                            break;
                        case LEFT:
                            direction = Direction.DOWN;
                            break;
                        case DOWN:
                            direction = Direction.RIGHT;
                            break;
                        case RIGHT:
                            direction = Direction.UP;
                            break;
                    }
                }
        }

        return true;
    }

}
