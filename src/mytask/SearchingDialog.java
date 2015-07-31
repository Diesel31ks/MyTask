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

import views.NewSearch;
import views.Search;
import views.TextEditor;

/**
 * 
 * @author adm.hromov.os
 *
 */
public class SearchingDialog extends TitleAreaDialog {

	private Text findTextField;
	private Text replaceTextField;

	private String find = "";
	private String replace = "";

	public SearchingDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		setTitle("SearchingDialog");
		setMessage("Search/Replace dialog", IMessageProvider.INFORMATION);
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
		if (buttonId == IDialogConstants.YES_TO_ALL_ID) {
			saveInput();
			new TextProcessor().replaceAll(find, replace);
			okPressed();
		}
		if (buttonId == IDialogConstants.OK_ID) {
			saveInput();
			new TextProcessor().findAll(find);
			okPressed();
		}
		if (buttonId == IDialogConstants.CANCEL_ID) {
			okPressed();
		}
	};

	class TextProcessor {
		private TextEditor textEditor = (TextEditor) Perspective
				.getView(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), TextEditor.ID);
		private Search search = (Search) Perspective.getView(PlatformUI.getWorkbench().getActiveWorkbenchWindow(),
				Search.ID);
		@SuppressWarnings("unused")
		private NewSearch newSearch = (NewSearch) Perspective
				.getView(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), NewSearch.ID);
		private StringArrayList strings = textEditor.getStrings();
		private StringArrayList newStringsOfTextEditor;

		public TextProcessor() {
		}

		// TODO need bugfixing:
		// Last string in TextEditor.textField disappears after "replaceAll()"
		private void replaceAll(String find, String replaceWith) {
			newStringsOfTextEditor = new StringArrayList();
			for (String s : strings) {
				if (s.contains(find.toLowerCase())) {
					int indexOfBegin = s.indexOf(find) - 3;
					if (indexOfBegin < 0)
						indexOfBegin = 0;
					int indexOfEnd = s.indexOf(find) + replaceWith.length() + 3;
					if (indexOfEnd >= s.length())
						indexOfEnd = s.length() - 1;
					char[] dst = new char[indexOfEnd - indexOfBegin];
					s = s.replace(find, replaceWith);
					s.getChars(indexOfBegin, indexOfEnd, dst, 0);
				}
				newStringsOfTextEditor.add(s + "\n");
			}
			textEditor.getTextField().setText(newStringsOfTextEditor.toString());
			findAll(replaceWith);
		}

		private void findAll(String find) {
			find = find.toLowerCase();
			strings = textEditor.getStrings();
			StringArrayList newStringsOfSearch = new StringArrayList();
			Iterator<String> iterator = strings.iterator();
			while (iterator.hasNext()) {
				StringBuilder currentString = new StringBuilder().append((String) iterator.next().toLowerCase());
				for (int i = 0; i <= currentString.length()+3; i++) {
					if (currentString.toString().contains(find)) {
						int indexOfBegin = currentString.indexOf(find) - 3;
						if (indexOfBegin < 0)
							indexOfBegin = 0;
						int indexOfEnd = currentString.indexOf(find) + find.length() + 3;
						if (indexOfEnd >= currentString.length())
							indexOfEnd = currentString.length() - 1;
						char[] dst = new char[indexOfEnd - indexOfBegin];
						currentString.getChars(indexOfBegin, indexOfEnd, dst, 0);
						newStringsOfSearch.add(String.valueOf(dst) + "\n");
						currentString.delete(0, currentString.indexOf(find) + find.length() + 1);
					}
				}
			}
			search.setTextFieldText(newStringsOfSearch.toString());
		}
	}

}