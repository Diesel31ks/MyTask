package mytask;

import java.util.Iterator;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import views.Search;
import views.TextEditor;

/**
 * Has a little bug at method findAllPressed()
 * 
 * @author adm.hromov.os
 *
 */
public class MyTitleAreaDialog extends TitleAreaDialog {
	private Text findTextField;
	private Text replaceTextField;

	private String find = "";
	private String replace = "";

	public MyTitleAreaDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		setTitle("Search/Replace dialog");
		setMessage("This is a TitleAreaDialog", IMessageProvider.INFORMATION);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		createFind(container);
		createReplace(container);

		return area;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.YES_TO_ALL_ID, "Replace all", true);
		createButton(parent, IDialogConstants.OK_ID, "Find all", false);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	private void createFind(Composite container) {
		Label findLabel = new Label(container, SWT.NONE);
		findLabel.setText("Find");

		GridData dataFind = new GridData();
		dataFind.grabExcessHorizontalSpace = true;
		dataFind.horizontalAlignment = GridData.FILL;

		findTextField = new Text(container, SWT.BORDER);
		findTextField.setText(find);
		findTextField.setLayoutData(dataFind);
	}

	private void createReplace(Composite container) {
		Label replaceLabel = new Label(container, SWT.NONE);
		replaceLabel.setText("Replace with");

		GridData dataReplace = new GridData();
		dataReplace.grabExcessHorizontalSpace = true;
		dataReplace.horizontalAlignment = GridData.FILL;
		replaceTextField = new Text(container, SWT.BORDER);
		replaceTextField.setText(replace);
		replaceTextField.setLayoutData(dataReplace);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	// save content of the Text fields because they get disposed
	// as soon as the Dialog closes
	private void saveInput() {
		find = findTextField.getText();
		replace = replaceTextField.getText();
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.YES_TO_ALL_ID)
			replaceAllPressed();
		if (buttonId == IDialogConstants.OK_ID)
			findAllPressed();
	};
	
	private void replaceAllPressed() {
		saveInput();
		TextEditor textEditor = (TextEditor) Perspective.getView(PlatformUI.getWorkbench().getActiveWorkbenchWindow(),
				TextEditor.ID);
		Search search = (Search) Perspective.getView(PlatformUI.getWorkbench().getActiveWorkbenchWindow(),Search.ID);
		
		StringArrayList strings = textEditor.getStrings();
		StringArrayList newStringsOfTextEditor = new StringArrayList();
		StringArrayList newStringsOfSearch = new StringArrayList();
		Iterator<String> iterator = strings.iterator();
		while (iterator.hasNext()) {
			String string = (String) iterator.next().toLowerCase();
			if (string.contains(find.toLowerCase())) {
				int indexOfBegin = string.indexOf(find) - 3;
				if (indexOfBegin < 0)
					while (indexOfBegin < 0)
						indexOfBegin += 1;
				int indexOfEnd = string.indexOf(find) + replace.length() + 3;
				if (indexOfEnd > string.length())
					while (indexOfEnd > string.length())
						indexOfEnd = string.length() - 1;
				char[] dst = new char[indexOfEnd - indexOfBegin];
				string = string.replace(find, replace);
				string.getChars(indexOfBegin, indexOfEnd, dst, 0);
				newStringsOfSearch.add(String.valueOf(dst) + "\n");
			}
			newStringsOfTextEditor.add(string + "\n");
		}
		textEditor.getTextField().setText(newStringsOfTextEditor.toString());
		search.setTextFieldText(newStringsOfSearch.toString());
		super.okPressed();
	}
	
	// TODO need bugfixing:
	// Last string in TextEditor.textField disappears
	// after pressing button "Replace all" 
	private void findAllPressed() {
		saveInput();
		TextEditor textEditor = (TextEditor) Perspective.getView(PlatformUI.getWorkbench().getActiveWorkbenchWindow(),
				TextEditor.ID);
		Search search = (Search) Perspective.getView(PlatformUI.getWorkbench().getActiveWorkbenchWindow(),Search.ID);
		StringArrayList strings = textEditor.getStrings();
		StringArrayList newStringsOfSearch = new StringArrayList();
		Iterator<String> iterator = strings.iterator();
		while (iterator.hasNext()) {
			String string = (String) iterator.next().toLowerCase();
			if (string.contains(find.toLowerCase())) {
				int indexOfBegin = string.indexOf(find) - 3;
				if (indexOfBegin < 0)
					while (indexOfBegin < 0)
						indexOfBegin += 1;
				int indexOfEnd = string.indexOf(find) + find.length() + 3;
				if (indexOfEnd > string.length())
					while (indexOfEnd > string.length())
						indexOfEnd = string.length() - 1;
				char[] dst = new char[indexOfEnd - indexOfBegin];
				string.getChars(indexOfBegin, indexOfEnd, dst, 0);
				newStringsOfSearch.add(String.valueOf(dst) + "\n");
			}
		}
		search.setTextFieldText(newStringsOfSearch.toString());
		super.okPressed();
	}

	public String getFind() {
		return find;
	}

	public String getReplace() {
		return replace;
	}
}