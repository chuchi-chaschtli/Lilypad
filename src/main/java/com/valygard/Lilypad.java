package com.valygard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.text.DefaultEditorKit;

import com.valygard.action.NewFileAction;
import com.valygard.action.OpenAction;
import com.valygard.action.QuitAction;
import com.valygard.action.RedoAction;
import com.valygard.action.SaveAction;
import com.valygard.action.SaveAsAction;
import com.valygard.action.UndoAction;
import com.valygard.undoable.UndoableManager;
import com.valygard.undoable.UndoableString;

/**
 * Main frame editor for the text editor.
 * 
 * @author Anand
 * @since 1.0
 */
public class Lilypad extends JFrame {

	private static final long serialVersionUID = 8413580632662011199L;

	// default dimensions
	public static final int DEF_ROWS = 40, DEF_COLUMNS = 110;

	// area which we can write documents
	private JTextArea area = new JTextArea(Lilypad.DEF_ROWS,
			Lilypad.DEF_COLUMNS);
	private JFileChooser dialog = new JFileChooser(
			System.getProperty("user.resources"));
	private String currFile = "Untitled";
	private boolean changed = false;

	// undo/redo
	private JButton undoButton, redoButton;
	private UndoableManager manager = new UndoableManager();

	// ----------------- //
	// Actions
	// ----------------- //

	private Action newFile = new NewFileAction(this);
	private Action open = new OpenAction(this);
	private Action save = new SaveAction(this);
	private Action saveAs = new SaveAsAction(this);
	private Action quit = new QuitAction(this);
	private Action undo = new UndoAction(this);
	private Action redo = new RedoAction(this);

	private ActionMap actionMap = area.getActionMap();
	private Action cut = actionMap.get(DefaultEditorKit.cutAction);
	private Action copy = actionMap.get(DefaultEditorKit.copyAction);
	private Action paste = actionMap.get(DefaultEditorKit.pasteAction);

	// ----------------- //
	// Constructor
	// ----------------- //

	/**
	 * Default constructor for UI
	 */
	public Lilypad() {
		// colors
		area.setSelectionColor(Color.YELLOW);
		area.setSelectedTextColor(Color.RED);
		// logistics
		area.setFont(new Font("Monospaced", Font.PLAIN, 12));
		JScrollPane scroll = new JScrollPane(area,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll, BorderLayout.CENTER);

		// add menu bar
		JMenuBar JMB = new JMenuBar();
		setJMenuBar(JMB);
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		JMB.add(file);
		JMB.add(edit);

		// add buttons
		file.add(newFile);
		file.add(open);
		file.add(save);
		file.add(quit);
		file.add(saveAs);
		file.addSeparator();

		// init icons
		for (int i = 0; i < 4; i++)
			file.getItem(i).setIcon(null);

		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
		edit.add(undo);
		edit.add(redo);

		edit.getItem(0).setText("Cut");
		edit.getItem(1).setText("Copy");
		edit.getItem(2).setText("Paste");
		edit.getItem(3).setText("Undo");
		edit.getItem(4).setText("Redo");

		JToolBar tool = new JToolBar();
		add(tool, BorderLayout.NORTH);
		tool.add(newFile);
		tool.add(open);
		tool.add(save);
		tool.add(saveAs);
		tool.addSeparator();

		JButton cutButton = tool.add(cut), copButton = tool.add(copy), pasteButton = tool
				.add(paste);
		undoButton = tool.add(undo);
		redoButton = tool.add(redo);

		cutButton.setText(null);
		cutButton.setIcon(retrieveImage("cut.gif"));
		copButton.setText(null);
		copButton.setIcon(retrieveImage("copy.gif"));
		pasteButton.setText(null);
		pasteButton.setIcon(retrieveImage("paste.gif"));
		undoButton.setText(null);
		undoButton.setIcon(retrieveImage("undo.gif"));
		redoButton.setText(null);
		redoButton.setIcon(retrieveImage("redo.gif"));

		save.setEnabled(false);
		saveAs.setEnabled(false);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		area.addKeyListener(k1);
		setTitle(currFile);
		setIconImage(retrieveImage("lilypad.gif").getImage());
		setVisible(true);

		refreshEdits();
	}

	private KeyListener k1 = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			changed = true;
			save.setEnabled(true);
			saveAs.setEnabled(true);
			if (!(e.isActionKey() || e.isAltDown() || e.isAltGraphDown() || e
					.isControlDown())) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_CAPS_LOCK:
				case KeyEvent.VK_NUM_LOCK:
				case KeyEvent.VK_PAGE_DOWN:
				case KeyEvent.VK_PAGE_UP:
				case KeyEvent.VK_DELETE:
				case KeyEvent.VK_SHIFT:
				case KeyEvent.VK_SCROLL_LOCK:
				case KeyEvent.VK_BACK_SPACE:
					break;
				default:
					manager.addUndoable(new UndoableString(String.valueOf(e
							.getKeyChar())));
				}
			}
			refreshEdits();
		}
	};

	public String getCurrentFile() {
		return currFile;
	}

	public JTextArea getTextArea() {
		return area;
	}

	public boolean isChanged() {
		return changed;
	}

	public JFileChooser getDialog() {
		return dialog;
	}

	public Action getSaveAsAction() {
		return saveAs;
	}

	public UndoableManager getUndoManager() {
		return manager;
	}

	// ----------------- //
	// Methods
	// ----------------- //

	/**
	 * Constructs a new file
	 */
	public void newFile() {
		currFile = "Untitled";
		this.setTitle(currFile);
		changed = false;
	}

	/**
	 * Convenience way to parse image icons.
	 * 
	 * @param path
	 *            the file path
	 * @return the image icon
	 */
	public ImageIcon retrieveImage(String path) {
		return new ImageIcon(getClass().getResource(path));
	}

	/**
	 * Attempts to read in the file by the given file name.
	 * 
	 * @throws an
	 *             IOException if no file of the given file name was found.
	 * @param fileName
	 *            the string file name
	 */
	public void readInFile(String fileName) {
		try {
			FileReader reader = new FileReader(fileName);
			area.read(reader, null);
			reader.close();
			currFile = fileName;
			this.setTitle(currFile);
			changed = false;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,
					"Lilypad can't find the file called " + fileName);
		}
	}

	/**
	 * Performs save as operation on the current file.
	 * 
	 * @see #saveFile(String)
	 */
	public void saveFileAs() {
		if (dialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
			saveFile(dialog.getSelectedFile().getAbsolutePath());
	}

	/**
	 * Attempts to save this file, by checking if the file has been changed and
	 * if the user wishes to save changes.
	 * 
	 * @see #saveFile(String)
	 */
	public void saveOldFile() {
		if (changed
				&& JOptionPane.showConfirmDialog(this,
						"Would you like to save " + currFile + " ?", "Save",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			saveFile(currFile);
	}

	/**
	 * Attempts to save the file of the given file name.
	 * 
	 * @throws IOException
	 *             if the file could not be saved.
	 * 
	 * @param fileName
	 *            the String name of the file.
	 */
	public void saveFile(String fileName) {
		try {
			FileWriter w = new FileWriter(fileName);
			area.write(w);
			w.close();
			currFile = fileName;
			setTitle(currFile);
			changed = false;
			save.setEnabled(false);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Lilypad could not save file!");
		}
	}

	/**
	 * Refresh the edit working list so that the user can only redo and undo
	 * when actually feasible.
	 */
	public void refreshEdits() {
		undoButton.setEnabled(manager.canUndo());
		undo.setEnabled(manager.canUndo());

		redoButton.setEnabled(manager.canRedo());
		redo.setEnabled(manager.canRedo());
	}

	public static void main(String[] args) {
		new Lilypad();
	}
}
