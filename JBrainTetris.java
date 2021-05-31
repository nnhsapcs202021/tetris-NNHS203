import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;

public class JBrainTetris extends JTetris
{
    private Brain brain; 
    private ArrayList<Brain> brainList; 
    private JComboBox brainCombo;
    private Move move;
    private boolean brainStatus = false; 
    private JButton enableBrain;
    /**
     * Constructor for objects of class JBrainTetris
     */
    public JBrainTetris(int width, int height)
    {
        super(width, height);
        

    }

    public Container createControlPanel() 
    {   Container panel = Box.createVerticalBox();

        // COUNT
        countLabel = new JLabel("0");
        panel.add(countLabel);
        
        // TIME 
        timeLabel = new JLabel(" ");
        panel.add(timeLabel);

        panel.add(Box.createVerticalStrut(12));
        
        // START button
        this.startButton = new JButton("Start");
        panel.add(this.startButton);
        this.startButton.addActionListener( new StartActionListener());
        
        // STOP button
        this.stopButton = new JButton("Stop");
        panel.add(this.stopButton);
        this.stopButton.addActionListener( new StopActionListener());
        
        this.enableButtons();
        
        JPanel row = new JPanel();
        
        // SPEED slider
        panel.add(Box.createVerticalStrut(12));
        row.add(new JLabel("Speed:"));
        this.speed = new JSlider(0, 200, 75);    // min, max, current
        this.speed.setPreferredSize(new Dimension(100,15));
        if (this.testMode)
        {
            this.speed.setValue(200);  // max for test mode
        }
        
        this.updateTimer();
        row.add(this.speed);
        
        panel.add(row);
        this.speed.addChangeListener( new UpdateTimerChangeListener());
        
        
        brainList = BrainFactory.createBrains();
        String[] brainName = new String[brainList.size()];
        for(int i=0; i<brainList.size(); i ++)
        {
        brainName[i] = brainList.get(i).toString();
        }
        brainCombo = new JComboBox(brainName);
        this.brainCombo.addActionListener(new ComboBoxListener());
        panel.add(brainCombo);
        
        this.enableBrain =new JButton("Enable Brain");
        panel.add(this.enableBrain);
        this.enableBrain.addActionListener(new BrainEnableListener());
        return panel;
    }
    
    public Piece pickNextPiece()
    {
        int pieceNum = (int)(this.pieces.length * this.random.nextDouble());
        return this.pieces[pieceNum];
    }
    
    private void enableButtons()
    {
        this.startButton.setEnabled(!this.gameOn);
        this.stopButton.setEnabled(this.gameOn);

    }
    
    private class ComboBoxListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            JComboBox cb = (JComboBox)e.getSource();
            int brainIndex = cb.getSelectedIndex();
            
            brain = brainList.get(brainIndex);
        }
    }
    
    private class BrainEnableListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            brainStatus = !brainStatus;
            enableBrain.setText("Brain ON = " + brainStatus);  
        }
    }
    
    private class StartActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            startGame();
        }
    }
    
    private class StopActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            stopGame();
        }
    }
    
    private class UpdateTimerChangeListener implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            updateTimer();
        }
    }
    
}
