package com.tempestsoul.dnd.ui;

import com.tempestsoul.dnd.d20.WildShapeService;
import com.tempestsoul.dnd.d20.WildShapeServiceImpl;
import com.tempestsoul.dnd.d20.model.Creature;
import com.tempestsoul.dnd.d20.model.CreatureType;
import com.tempestsoul.dnd.d20.model.Size;
import com.tempestsoul.dnd.service.CharacterLoadService;
import com.tempestsoul.dnd.service.CsvShapeParser;
import com.tempestsoul.dnd.service.HeroForgeLoadService;
import com.tempestsoul.dnd.service.ShapeLoadService;
import com.tempestsoul.dnd.ui.components.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WildShapeInputForm implements Runnable {

    private JFrame myFrame;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem openMenuItem;
    private JPanel basePanel;
    private JList wildShapeList;
    private JTable druidStats;
    private JPanel inputPanel;
    private JPanel wildShapeSelectPanel;
    private JComboBox shapeTypeDropdown;
    private JScrollPane wildShapeScroll;
    private JTextArea log;
    private JLabel druidName;
    private JScrollPane druidStatsScroll;
    private JTable druidSkills;
    private JScrollPane druidSkillsScroll;
    private JTable shapeStats;
    private JPanel shapePanel;
    private JTable shapeAttacks;
    private JTextField druidLvl;
    private JButton wildShapeButton;

    private WildShapeService wildShapeService = new WildShapeServiceImpl();
    private CharacterLoadService characterLoadService = new HeroForgeLoadService();
    private ShapeLoadService shapeLoadService = new CsvShapeParser();
    private CreatureRender creatureRender = new CreatureRender();

    private Creature druid = null;

    public WildShapeInputForm() {
        myFrame = new JFrame("d20 Wild Shape Calculator");
        myFrame.setContentPane(basePanel);
        final JFileChooser fc = new JFileChooser();
        fc.addChoosableFileFilter(new FileNameExtensionFilter("HeroForge files", "hfg", "xls"));
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        openMenuItem = new JMenuItem("Open...", KeyEvent.VK_O);
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openMenuItem.addActionListener(e -> {
            int returnVal = fc.showOpenDialog(basePanel);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would open the file.
                loadCharacter(file);
                log.append("Opening: " + file.getName() + "." + System.lineSeparator());
            } else {
                log.append("Open command cancelled by user." + System.lineSeparator());
            }
        });

        fileMenu.add(openMenuItem);
        JMenuItem exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        exitMenuItem.addActionListener(e -> {
            int confirm = JOptionPane.showOptionDialog(myFrame,
                    "Are You Sure to Close this Application?",
                    "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        myFrame.setJMenuBar(menuBar);
        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(myFrame,
                        "Are You Sure to Close this Application?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        };
        myFrame.addWindowListener(exitListener);
        wildShapeList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList list = (JList) e.getSource();
                Creature creature = ((ShapeListModel) list.getModel()).getElementAt(list.getSelectedIndex());
                ((AbilityTableModel) shapeStats.getModel()).setCreature(creature);
                ((AttacksTableModel) shapeAttacks.getModel()).setCreature(creature);
            }
        });
        loadCreatures();

        druidStats.setModel(new AbilityTableModel(druid));
        druidSkills.setModel(new SkillsTableModel(druid));
        shapeStats.setModel(new AbilityTableModel(null));
        shapeAttacks.setModel(new AttacksTableModel(null));

        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        shapeTypeDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String shapeName = (String) cb.getSelectedItem();
                if (shapeName.equals("<Select a Filter>"))
                    filterShapes(null);
                else
                    filterShapes(CreatureType.valueOf(shapeName));
            }
        });
        myFrame.pack();
        wildShapeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (druid != null && wildShapeList.getSelectedIndex() >= 0) {
                    Creature shapedDruid = wildShapeService.wildShape(druid, (Creature) wildShapeList.getSelectedValue());
                    JTextArea textArea = new JTextArea(creatureRender.render(shapedDruid));
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    textArea.setEnabled(false);
                    scrollPane.setPreferredSize(new Dimension(500, 700));
                    JOptionPane.showMessageDialog(myFrame, scrollPane, "Wild-Shaped Druid",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(myFrame,
                            "Load a druid & select a wild shape",
                            "Inane error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new WildShapeInputForm());
    }

    @Override
    public void run() {
        myFrame.setVisible(true);
    }

    private void loadCreatures() {
        List<Creature> creatureList = shapeLoadService.loadShapes();
        wildShapeList.setModel(new ShapeListModel(creatureList));
    }

    private void loadCharacter(File file) {
        druid = characterLoadService.loadCharacter(file);
        druidName.setText(druid.getName());
        ((AbilityTableModel) druidStats.getModel()).setCreature(druid);
        ((SkillsTableModel) druidSkills.getModel()).setCreature(druid);
        druidLvl.setText(String.valueOf(druid.getDruidLvl()));
    }

    private void filterShapes(final CreatureType type) {
        ((ShapeListModel) wildShapeList.getModel()).filter(c -> {
            if (type == null) return true;
            boolean matches = type.equals(c.getType());
            matches &= c.getNumHitDice() <= druid.getDruidLvl();
            if (druid.getDruidLvl() < 5) {
                matches = false;
            }
            return matches;
        });
        wildShapeList.repaint();
    }

    private void createUIComponents() {
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        basePanel = new JPanel();
        basePanel.setLayout(new GridBagLayout());
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        basePanel.add(inputPanel, gbc);
        druidName = new JLabel();
        druidName.setText("Select a Druid!");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(druidName, gbc);
        druidStatsScroll = new JScrollPane();
        druidStatsScroll.setHorizontalScrollBarPolicy(30);
        druidStatsScroll.setVerticalScrollBarPolicy(21);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        inputPanel.add(druidStatsScroll, gbc);
        druidStatsScroll.setBorder(BorderFactory.createTitledBorder("Druid Stats"));
        druidStats = new JTable();
        druidStats.setAutoResizeMode(4);
        druidStats.setFillsViewportHeight(false);
        druidStatsScroll.setViewportView(druidStats);
        druidSkillsScroll = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        inputPanel.add(druidSkillsScroll, gbc);
        druidSkillsScroll.setBorder(BorderFactory.createTitledBorder("Druid Skills"));
        druidSkills = new JTable();
        druidSkills.setAutoResizeMode(4);
        druidSkills.setFillsViewportHeight(true);
        druidSkillsScroll.setViewportView(druidSkills);
        final JLabel label1 = new JLabel();
        label1.setText("Druid Level");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(label1, gbc);
        druidLvl = new JTextField();
        druidLvl.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(druidLvl, gbc);
        log = new JTextArea();
        log.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        basePanel.add(log, gbc);
        final JSplitPane splitPane1 = new JSplitPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        basePanel.add(splitPane1, gbc);
        wildShapeSelectPanel = new JPanel();
        wildShapeSelectPanel.setLayout(new GridBagLayout());
        splitPane1.setLeftComponent(wildShapeSelectPanel);
        shapeTypeDropdown = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("<Select a Filter>");
        defaultComboBoxModel1.addElement("Animal");
        defaultComboBoxModel1.addElement("Plant");
        defaultComboBoxModel1.addElement("Elemental");
        shapeTypeDropdown.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        wildShapeSelectPanel.add(shapeTypeDropdown, gbc);
        wildShapeScroll = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        wildShapeSelectPanel.add(wildShapeScroll, gbc);
        wildShapeList = new JList();
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        wildShapeList.setModel(defaultListModel1);
        wildShapeList.setSelectionMode(0);
        wildShapeScroll.setViewportView(wildShapeList);
        shapePanel = new JPanel();
        shapePanel.setLayout(new GridBagLayout());
        splitPane1.setRightComponent(shapePanel);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        shapePanel.add(panel1, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(scrollPane1, gbc);
        scrollPane1.setBorder(BorderFactory.createTitledBorder("Shape Stats"));
        shapeStats = new JTable();
        shapeStats.setAutoResizeMode(4);
        shapeStats.setPreferredScrollableViewportSize(new Dimension(128, 128));
        shapeStats.setToolTipText("The selected wild shape's stats");
        scrollPane1.setViewportView(shapeStats);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        shapePanel.add(panel2, gbc);
        final JScrollPane scrollPane2 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(scrollPane2, gbc);
        scrollPane2.setBorder(BorderFactory.createTitledBorder("Shape Attacks"));
        shapeAttacks = new JTable();
        shapeAttacks.setAutoResizeMode(4);
        shapeAttacks.setPreferredScrollableViewportSize(new Dimension(250, 400));
        shapeAttacks.setToolTipText("The selected wild shape's attacks");
        scrollPane2.setViewportView(shapeAttacks);
        wildShapeButton = new JButton();
        wildShapeButton.setText("Wild Shape");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        shapePanel.add(wildShapeButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return basePanel;
    }
}
