/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author 10188023
 */
public class ScoreBoard extends javax.swing.JPanel implements ScoreBoardIncrementer{

  
    /** Creates new form ScoreBoard */
    public ScoreBoard() {
        initComponents();
        incrementScore(0);
    }
    private int score;
    public void incrementScore(int increment){
        score += increment;
        jLabel1.setText(""+score);
    }
    public int getScore(){
        return score;
    }
     public void checkRecord() throws IOException{
        File scoreFile= new File("scooreRecord.txt");
        scoreFile.createNewFile();
        int oldScore=getRecord(scoreFile);
        if(oldScore<score){
            writeRecord(scoreFile, score);
        }
    }
    private int getRecord(File scoreFile) throws FileNotFoundException, IOException{
        
        FileReader input = new FileReader(scoreFile);
        int scoreRecord=0;
        try {
            scoreRecord=input.read();
        }finally{
            input.close();       
        }
        return scoreRecord;
    }
    private void writeRecord(File scoreFile,int newScore) throws IOException{
        scoreFile.delete();
        scoreFile.createNewFile();
        FileWriter writer = new FileWriter(scoreFile);
        try {
            writer.write(newScore);
        }finally{
            writer.close();
        }
        
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(339, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

}