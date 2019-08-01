package com.ecs160group.pacman;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapCreator {

    //needed to offset the bitmapdraw
    private final float pacGhostRadius;

    //private Location[] locations;
    public Bitmap[] bitmaps;

    public Bitmap[] pacMaps;

    public Context context;

    private final int xScaled;
    private final int yScaled;

    public Bitmap[] pacLeftMaps;
    public Bitmap[] pacRightMaps;
    public Bitmap[] pacUpMaps;
    public Bitmap[] pacDownMaps;

    public Bitmap[] blinkyLeftMaps;
    public Bitmap[] blinkyRightMaps;
    public Bitmap[] blinkyUpMaps;
    public Bitmap[] blinkyDownMaps;

    public Bitmap[] inkyLeftMaps;
    public Bitmap[] inkyRightMaps;
    public Bitmap[] inkyUpMaps;
    public Bitmap[] inkyDownMaps;

    public Bitmap[] pinkyLeftMaps;
    public Bitmap[] pinkyRightMaps;
    public Bitmap[] pinkyUpMaps;
    public Bitmap[] pinkyDownMaps;

    public Bitmap[] clydeLeftMaps;
    public Bitmap[] clydeRightMaps;
    public Bitmap[] clydeUpMaps;
    public Bitmap[] clydeDownMaps;

    public Bitmap[] scaredGhostMaps;

    public BitmapCreator(PacmanGame game) {

        this.context = game.activityContext;
        xScaled = game.xScaled;
        yScaled = game.yScaled;
        pacGhostRadius = game.PacGhostRadius;

        pacLeftMaps = new Bitmap[3];
        pacRightMaps = new Bitmap[3];
        pacUpMaps = new Bitmap[3];
        pacDownMaps = new Bitmap[3];

        blinkyLeftMaps = new Bitmap[2];
        blinkyRightMaps = new Bitmap[2];
        blinkyUpMaps = new Bitmap[2];
        blinkyDownMaps = new Bitmap[2];

        inkyLeftMaps = new Bitmap[2];
        inkyRightMaps = new Bitmap[2];
        inkyUpMaps = new Bitmap[2];
        inkyDownMaps = new Bitmap[2];

        pinkyLeftMaps = new Bitmap[2];
        pinkyRightMaps = new Bitmap[2];
        pinkyUpMaps = new Bitmap[2];
        pinkyDownMaps = new Bitmap[2];

        clydeLeftMaps = new Bitmap[2];
        clydeRightMaps = new Bitmap[2];
        clydeUpMaps = new Bitmap[2];
        clydeDownMaps = new Bitmap[2];

        scaredGhostMaps = new Bitmap[2];

        //initialize all bitmaps
        initAllMaps();

    }


    /**
     * calls all separate pac/ghost initialization methods
     */
    private void initAllMaps(){
        initAllPacMaps();
        initAllBlinkyMaps();
        initAllInkyMaps();
        initAllPinkyMaps();
        initAllClydeMaps();
        initScaredGhostMaps();
    }

    /**
     * method to initialize all pacmaps, calls initpacmaps four times (one for every direction)
     */
    private void initAllPacMaps(){
        initPacMaps('l');
        initPacMaps('r');
        initPacMaps('u');
        initPacMaps('d');
    }

    /**
     * method that helps initialize all pacman bitmaps,called by initAllPacMaps
     * @param facingDirection
     */
    private void initPacMaps(char facingDirection) {
        Bitmap b;
        Bitmap sizedB;
        switch (facingDirection) {
            case 'l':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmanl1);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pacLeftMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmanl2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pacLeftMaps[1] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmana3);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pacLeftMaps[2] = sizedB;
                break;
            case 'r':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmanr1);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pacRightMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmanr2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pacRightMaps[1] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmana3);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pacRightMaps[2] = sizedB;
                break;
            case 'u':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmanu1);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pacUpMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmanu2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pacUpMaps[1] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmana3);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pacUpMaps[2] = sizedB;
                break;
            case 'd':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmand1);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pacDownMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmand2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pacDownMaps[1] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacmana3);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pacDownMaps[2] = sizedB;
                break;
            default:
                break;
        } //end switch
    }

    /**
     * method to initialize all blinkyMaps, calls initblinkymaps four times (one for every direction)
     */
    private void initAllBlinkyMaps(){
        initBlinkyMaps('l');
        initBlinkyMaps('r');
        initBlinkyMaps('u');
        initBlinkyMaps('d');
    }

    /**
     * method that helps initialize all blinky bitmaps,called by initAllPacMaps
     * @param facingDirection
     */
    private void initBlinkyMaps(char facingDirection) {
        Bitmap b;
        Bitmap sizedB;
        switch (facingDirection) {
            case 'l':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.blinkyleft1);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                blinkyLeftMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.blinkyleft2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                blinkyLeftMaps[1] = sizedB;
                break;
            case 'r':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.blinkyright1);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                blinkyRightMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.blinkyright2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                blinkyRightMaps[1] = sizedB;
                break;
            case 'u':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.blinkyup1);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                blinkyUpMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.blinkyup2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                blinkyUpMaps[1] = sizedB;
                break;
            case 'd':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.blinkydown1);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                blinkyDownMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.blinkydown2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                blinkyDownMaps[1] = sizedB;
                break;
            default:
                break;
        } //end switch
    }

    /**
     * method to initialize all inkyMaps, calls initInkyMaps four times (one for every direction)
     */
    private void initAllInkyMaps(){
        initInkyMaps('l');
        initInkyMaps('r');
        initInkyMaps('u');
        initInkyMaps('d');
    }

    /**
     * method that helps initialize all inky bitmaps,called by initAllInkyMaps
     * @param facingDirection
     */
    private void initInkyMaps(char facingDirection) {
        Bitmap b;
        Bitmap sizedB;
        switch (facingDirection) {
            case 'l':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.inkyleft1);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                inkyLeftMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.inkyleft2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                inkyLeftMaps[1] = sizedB;
                break;
            case 'r':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.inkyright1);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                inkyRightMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.inkyright2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                inkyRightMaps[1] = sizedB;
                break;
            case 'u':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.inkyup1);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                inkyUpMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.inkyup2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                inkyUpMaps[1] = sizedB;
                break;
            case 'd':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.inkydown1);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                inkyDownMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.inkydown2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                inkyDownMaps[1] = sizedB;
                break;
            default:
                break;
        } //end switch
    }


    /**
     * method to initialize all inkyMaps, calls initinkyMaps four times (one for every direction)
     */
    private void initAllPinkyMaps(){
        initPinkyMaps('l');
        initPinkyMaps('r');
        initPinkyMaps('u');
        initPinkyMaps('d');
    }

    /**
     * method that helps initialize all pinky bitmaps,called by initAllPinkyMaps
     * @param facingDirection
     */
    private void initPinkyMaps(char facingDirection) {
        Bitmap b;
        Bitmap sizedB;
        switch (facingDirection) {
            case 'l':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pinkyleft1);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pinkyLeftMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pinkyleft2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pinkyLeftMaps[1] = sizedB;
                break;
            case 'r':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pinkyright1);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pinkyRightMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pinkyright2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pinkyRightMaps[1] = sizedB;
                break;
            case 'u':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pinkyup1);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pinkyUpMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pinkyup2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pinkyUpMaps[1] = sizedB;
                break;
            case 'd':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pinkydown1);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pinkyDownMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pinkydown2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                pinkyDownMaps[1] = sizedB;
                break;
            default:
                break;
        } //end switch
    }

    /**
     * method to initialize all ClydeMaps, calls initClydeMaps four times (one for every direction)
     */
    private void initAllClydeMaps(){
        initClydeMaps('l');
        initClydeMaps('r');
        initClydeMaps('u');
        initClydeMaps('d');
    }


    /**
     * method that helps initialize all clyde bitmaps,called by initAllClydeMaps
     * @param facingDirection
     */
    private void initClydeMaps(char facingDirection) {
        Bitmap b;
        Bitmap sizedB;
        switch (facingDirection) {
            case 'l':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.clydeleft2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                clydeLeftMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.clydeleft2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                clydeLeftMaps[1] = sizedB;
                break;
            case 'r':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.clyderight1);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                clydeRightMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.clyderight2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                clydeRightMaps[1] = sizedB;
                break;
            case 'u':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.clydeup1);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                clydeUpMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.clydeup2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                clydeUpMaps[1] = sizedB;
                break;
            case 'd':
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.clydedown1);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                clydeDownMaps[0] = sizedB;

                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.clydedown2);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                clydeDownMaps[1] = sizedB;
                break;
            default:
                break;
        } //end switch
    }

    /**
     * method that initializes scared ghost bitmap
     */

    private void initScaredGhostMaps(){
        Bitmap b;
        Bitmap sizedB;
        b = BitmapFactory.decodeResource(context.getResources(), R.drawable.ghostweak1);
        sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                (int) pacGhostRadius * 2, false);
                scaredGhostMaps[0] = sizedB;
        b = BitmapFactory.decodeResource(context.getResources(), R.drawable.ghostweak2);
        sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                (int) pacGhostRadius * 2, false);
        scaredGhostMaps[1] = sizedB;

    }





}
