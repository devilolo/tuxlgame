/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import env3d.Env;
import env3d.advanced.EnvNode;

/**
 *
 * @author lemairlo
 */
public class Letter extends EnvNode {

    private char letter;
    private Env env;

    public Letter(char l, double x, double y, Env env) {
        letter = Character.toLowerCase(l);
        this.env = env;
        setX(x);
        setZ(y);
        setY(5.0);
        if (letter == ' ') {
            setTexture("models/letter/cube.png");
        } else {
            setTexture("models/letter/" + letter + ".png");
        }
        setModel("models/letter/cube.obj");
        setScale(5.0);
    }
}
