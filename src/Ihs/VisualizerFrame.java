package Ihs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class VisualizerFrame extends JFrame {
    private final int MAX_SPEED = 1000;
    private final int MIN_SPEED = 1;
    private final int MAX_SIZE = 500;
    private final int MIN_SIZE = 1;
    private final int DEFAULT_SPEED = 20;
    private final int DEFAULT_SIZE = 100;

    private final String[] SortsAlgo = {
            "Bubble", "Selection", "Insertion", "Merge",
            "Radix LSD", "Radix MSD", "Shell", "Bubble(fast)",
            "Selection(fast)", "Insertion(fast)"
    };

    private int sizeModifier;

    private JPanel wrapper;
    private JPanel arrayWrapper;
    private JPanel buttonWrapper;
    private JPanel[] squarePanels;
    private JButton start;
    private JComboBox<String> selection;
    private JSlider speed;
    private JSlider size;
    private JLabel speedVal;
    private JLabel sizeVal;
    private GridBagConstraints gbc;
    private JCheckBox stepped;

    public VisualizerFrame() {
        super("SortAlgo Visualizer");

        start = new JButton("Start");
        buttonWrapper = new JPanel();
        arrayWrapper = new JPanel();
        wrapper = new JPanel();
        selection = new JComboBox<String>();
        speed = new JSlider(MIN_SPEED, MAX_SPEED, DEFAULT_SPEED);
        size = new JSlider(MIN_SIZE, MAX_SIZE, DEFAULT_SIZE);
        speedVal = new JLabel("Speed: 20 ms");
        sizeVal = new JLabel("Size: 100 values");
        stepped = new JCheckBox("Stepped Values");
        gbc = new GridBagConstraints();

        for (String s: SortsAlgo)
            selection.addItem(s);

        arrayWrapper.setLayout(new GridBagLayout());
        wrapper.setLayout(new BorderLayout());

        gbc.insets = new Insets(0,1,0,1);
        gbc.anchor = GridBagConstraints.SOUTH;

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SortingVisualizer.startSort((String) selection.getSelectedItem());
            }
        });

        stepped.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SortingVisualizer.stepped = stepped.isSelected();
            }
        });

        speed.setMinorTickSpacing(10);
        speed.setMajorTickSpacing(100);
        speed.setPaintTicks(true);

        speed.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                speedVal.setText(("Speed: " + Integer.toString(
                        speed.getValue()) + "ms"
                ));
                validate();
                SortingVisualizer.sleep = speed.getValue();
            }
        });

        size.setMinorTickSpacing(10);
        size.setMajorTickSpacing(100);
        size.setPaintTicks(true);

        size.addChangeListener(arg0 -> {
            sizeVal.setText(("Size: " + Integer.toString(size.getValue()) + " values"));
            validate();
            SortingVisualizer.sortDataCount = size.getValue();
        });

        buttonWrapper.add(stepped);
        buttonWrapper.add(speedVal);
        buttonWrapper.add(speed);
        buttonWrapper.add(sizeVal);
        buttonWrapper.add(size);
        buttonWrapper.add(start);
        buttonWrapper.add(selection);

        wrapper.add(buttonWrapper, BorderLayout.SOUTH);
        wrapper.add(arrayWrapper);

        add(wrapper);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Rest the sizeModifier
                sizeModifier = (int) ((getHeight() * 0.9)/ (squarePanels.length));
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                // Empty
            }

            @Override
            public void componentShown(ComponentEvent e) {
                // Empty
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                // Empty
            }
        });

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // PreDrawArray reinitialize the array of panels
    public void preDrawArray(Integer[] squares)
    {
        squarePanels = new JPanel[SortingVisualizer.sortDataCount];
        arrayWrapper.removeAll();
        sizeModifier = (int)((getHeight() * 0.9) / (squarePanels.length));

        for (int i = 0; i < SortingVisualizer.sortDataCount; i++)
        {
            squarePanels[i] = new JPanel();
            squarePanels[i].setPreferredSize(new Dimension(SortingVisualizer.blockWidth,
                                            squares[i] * sizeModifier ));
            squarePanels[i].setBackground(Color.blue);
            arrayWrapper.add(squarePanels[i], gbc);
        }
        repaint();
        validate();
    }

    public void reDrawArray(Integer[] x) {
        reDrawArray(x, -1);
    }

    public void reDrawArray(Integer[] x, int y) {
        reDrawArray(x, y, -1);
    }

    public void reDrawArray(Integer[] x, int y, int z) {
        reDrawArray(x, y, z,-1);
    }

    // reDrawArray does similar to preDrawArray except it doesn't reinitialize the panel array
    public void reDrawArray(Integer[] squares, int working, int comparing, int reading)
    {
        arrayWrapper.removeAll();
        for(int i = 0; i < squarePanels.length; i++)
        {
            squarePanels[i] = new JPanel();
            squarePanels[i].setPreferredSize(new Dimension(
                    SortingVisualizer.blockWidth, squares[i] * sizeModifier
            ));
            if(i == working) {
                squarePanels[i].setBackground(Color.green);
            }
            else if(i == comparing) {
                squarePanels[i].setBackground(Color.red);
            }
            else if(i == reading) {
                squarePanels[i].setBackground(Color.yellow);
            }
            else{
                squarePanels[i].setBackground(Color.blue);
            }
            arrayWrapper.add(squarePanels[i], gbc);
        }
        repaint();
        validate();
    }

}
