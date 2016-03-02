package fileTest;

import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JButton;
import javax.swing.JLabel;

public class MainClass2
{

	private JFrame frame;
	private JTextField fileText;
	private JTextPane metaPane;
	private FileDialog fd = null;
	private JButton btnSelectFile;
	private JLabel lblStep;
	private JTextField stepField;
	private JButton btnCutTheShit;
	private Files oFiles;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainClass2 window = new MainClass2();
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainClass2()
	{
		initialize();
		//aufruf();
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(getFileText());
		frame.getContentPane().add(getMetaPane());
		frame.getContentPane().add(getBtnSelectFile());
		frame.getContentPane().add(getLblStep());
		frame.getContentPane().add(getStepField());
		frame.getContentPane().add(getBtnCutTheShit());
		
		
		
	}
	
	public void aufruf()
	{
		//Files.move() in Schleife alle Files in angelegten Ordner verschieben
		//prüfen ob in step etwas eingegeben worden ist
		fd = new FileDialog(new Frame());
		fd.setMultipleMode(true);
		fd.setMode(FileDialog.LOAD);
		Path path = Paths.get("");
		fd.setDirectory(path.toString());
		fd.setVisible(true);
		this.getFileText().setText(fd.getDirectory());
	
		Path oPath = Paths.get(oneFolderAbove(fd.getDirectory()));
		Path newFolderPath = createNewFolder(oPath);
		try
		{
			if(this.oFiles.list(FileSystems.getDefault().getPath(fd.getDirectory())).count() > Integer.valueOf(getStepField().getText())){
				long max = this.oFiles.list(FileSystems.getDefault().getPath(fd.getDirectory())).count();
				//Path oPath = FileSystems.getDefault().getPath()
				for(int i = 0; i < max - 3; i += Integer.valueOf(getStepField().getText())-1){
					
					Path sourcePath = this.oFiles.list(FileSystems.getDefault().getPath(fd.getDirectory())).skip(i).findAny().get();
					System.out.println("SourcePath: ");
					System.out.println(sourcePath.toString());
					Files.move(sourcePath, newFolderPath);
					//createNewFolder(FileSystems.getDefault().getPath(oneFolderAbove(fd.getDirectory()+"/TestOrdner")));
				}
			}
			System.out.println(this.oFiles.list(FileSystems.getDefault().getPath(fd.getDirectory())).count());
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private Path createNewFolder(Path s){
		
		Path newPath;
		try
		{
			newPath = Files.createDirectory(s);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		newPath = Paths.get(newPath.toString() + "/");
		System.out.println("+++++++++++");
		System.out.println(newPath.toString());
		return newPath;
	}
	/**
	 * Cuts the last folder out of the String
	 * @param s the actual folder path
	 * @return the new folder path with the last folder cutted out
	 */
	private String oneFolderAbove(String s){
		String[] splittedString = s.split("/");
		String newString = "";
		for(int i = 0; i < splittedString.length-1; i++) {
				newString += splittedString[i] + "/";
		}
		newString += "TetFolder/";
		return newString;
	}
	public void fileMeta()
	{
		if(fd.getFile() != null)
		{
			Path select = Paths.get(fd.getDirectory()+"/"+fd.getFile());
			try
			{
				getMetaPane().setText(Files.getLastModifiedTime(select).toString() + fd.getFile());
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private JTextField getFileText() {
		if (fileText == null) {
			fileText = new JTextField();
			fileText.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fd.setVisible(true);
					fileMeta();
				}
			});
			fileText.setBounds(6, 26, 214, 28);
			fileText.setColumns(10);
		}
		return fileText;
	}
	private JTextPane getMetaPane() {
		if (metaPane == null) {
			metaPane = new JTextPane();
			metaPane.setBounds(232, 23, 194, 93);
		}
		return metaPane;
	}
	private JButton getBtnSelectFile() {
		if (btnSelectFile == null) {
			btnSelectFile = new JButton("Select FIle");
			btnSelectFile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					aufruf();
				}
			});
			btnSelectFile.setBounds(38, 66, 117, 29);
		}
		return btnSelectFile;
	}
	private JLabel getLblStep() {
		if (lblStep == null) {
			lblStep = new JLabel("Step:");
			lblStep.setBounds(6, 125, 61, 16);
		}
		return lblStep;
	}
	private JTextField getStepField() {
		if (stepField == null) {
			stepField = new JTextField();
			stepField.setBounds(38, 119, 45, 28);
			stepField.setColumns(10);
		}
		return stepField;
	}
	private JButton getBtnCutTheShit() {
		if (btnCutTheShit == null) {
			btnCutTheShit = new JButton("Cut the shit out the folder");
			btnCutTheShit.setBounds(6, 243, 214, 29);
		}
		return btnCutTheShit;
	}
}
