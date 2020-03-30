/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.IOException;

/**
 *
 * @author 10188023
 */
public interface ScoreBoardIncrementer {
    public void incrementScore(int increment);
    public int  getScore();
    public void checkRecord() throws IOException;
}
