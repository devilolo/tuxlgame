/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import env3d.Env;
import env3d.advanced.EnvNode;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author lemairlo
 */
public class Tux extends EnvNode {

    private Env env;
    private Room room;
    private int camera;

    public Tux(Env env, Room room) {
        this.env = env;
        this.room = room;
        setScale(4.0);
        setX(room.getWidth() / 2);// positionnement au milieu de la largeur de la room
        setY(getScale() * 1.1); // positionnement en hauteur basé sur la taille de Tux
        setZ(room.getDepth() / 2); // positionnement au milieu de la profondeur de la room
        setTexture("models/tux/tux.png");
        setModel("models/tux/tux.obj");
        this.camera = 0;
    }

    public void déplace() {
        if (env.getKeyDown(Keyboard.KEY_Z) || env.getKeyDown(Keyboard.KEY_UP)) {
            this.setRotateY(180);
            if (testRoomCollision(this.getX(), this.getZ() - 3)) {
                this.setZ(this.getZ() - 1.0);
                if(camera == 1){
                    env.setCameraXYZ(this.getX(), 10, this.getZ()+10/*envance depth*/);
                    env.setCameraYaw(0);
                }
            }
        }
        if (env.getKeyDown(Keyboard.KEY_Q) || env.getKeyDown(Keyboard.KEY_LEFT)) {
            this.setRotateY(270);
            if (testRoomCollision(this.getX() - 3.0, this.getZ())) {
                this.setX(this.getX() - 1.0);
                if(camera == 1){
                    env.setCameraXYZ(this.getX()+10, 10, this.getZ()/*envance depth*/);
                    env.setCameraYaw(90);
                }
            }
        }
        if (env.getKeyDown(Keyboard.KEY_S) || env.getKeyDown(Keyboard.KEY_DOWN)) {
            this.setRotateY(0);
            if (testRoomCollision(this.getX(), this.getZ() + 3.0)) {
                this.setZ(this.getZ() + 1.0);
                if(camera == 1){
                    env.setCameraXYZ(this.getX(), 10, this.getZ()-10/*envance depth*/);
                    env.setCameraYaw(180);                   
                }
            }
        }
        if (env.getKeyDown(Keyboard.KEY_D) || env.getKeyDown(Keyboard.KEY_RIGHT)) {
            this.setRotateY(90);
            if (testRoomCollision(this.getX() + 3.0, this.getZ())) {
                this.setX(this.getX() + 1.0);
                if(camera == 1){
                    env.setCameraXYZ(this.getX()-10, 10, this.getZ()/*envance depth*/);
                    env.setCameraYaw(270);
                }
            }
        }
    }

    
    //ici on gere les changement de vue des personnage F normal G premiere personne , H vue du dessus attention vue du dessus la camera a defois des bug dangle
    public void changeView(ArrayList<Letter> letters) {
        if (env.getKeyDown(Keyboard.KEY_F) && this.camera != 0) {
            this.camera = 0;
            env.setCameraXYZ(50, 60, 175);
            env.setCameraPitch(-20);
            env.setCameraYaw(0);
            room.setTextureSouth(null);
            env.setRoom(room);
            env.addObject(this);
            for (Letter lettre : letters) {
                env.addObject(lettre);
            }
            
        }
        if (env.getKeyDown(Keyboard.KEY_G) && this.camera != 1) {
            this.camera = 1;
            env.setCameraXYZ(this.getX(), 10, this.getZ()/*envance depth*/);
            env.setCameraPitch(0);  
            room.setTextureSouth("textures/skybox/interstellar/south.png");
            env.setRoom(room);
            env.addObject(this);
            env.setCameraYaw(this.getRotateY());
            for (Letter lettre : letters) {
                env.addObject(lettre);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_H) && this.camera != 2) {
            this.camera = 2;
            env.setCameraXYZ(room.getWidth()/2,room.getHeight()*3, room.getDepth()/2);            
            env.setCameraPitch(-90);  
            env.setCameraYaw(0);
            //env.setCameraYaw(180);
                       
            room.setTextureSouth("textures/skybox/interstellar/south.png");
            env.setRoom(room);
            env.addObject(this);
            for (Letter lettre : letters) {
                env.addObject(lettre);
            }
        }
    }

    public Boolean testRoomCollision(double x, double z) {
        if ((x >= 0 && x <= room.getWidth()) && (z >= 0 && z <= room.getDepth())) {
            return true;
        } else {
            return false;
        }
    }

}
