package views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import mytask.Perspective;
import mytask.StringArrayList;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

/**
 * This is view for searching text
 * 
 * @author adm.hromov.os
 *
 */
public class Search extends ViewPart {
	public static final String ID = "views.Search";

	private static StringArrayList matchedList = new StringArrayList();
	private Text textField;

	public Search() {
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		{
			textField = new Text(container, SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL | SWT.MULTI);
			textField.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseDoubleClick(MouseEvent e) {
					TextEditor editor = (TextEditor) Perspective
							.getView(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), TextEditor.ID);
					
					String selectedText = ((Text) e.widget).getSelectionText();
					String textOfTextEditor = editor.getTextField().getText();
					int start = 0;
					int end = 0;
					if (textOfTextEditor.contains(selectedText)) {
						start = textOfTextEditor.indexOf(selectedText);
						end = start + selectedText.length();
						editor.getTextField().setSelection(start, end);
					}
				}
			});
			textField.setEditable(true);
			textField.setBounds(10, 10, 370, 225);
		}
	}

	@Override
	public void setFocus() {
	}

	public String getTextFieldText() {
		return textField.getText();
	}

	public void setTextFieldText(String text) {
		textField.setText(text);
	}

	public static StringArrayList getMatchedList() {
		return matchedList;
	}

	public static void setMatchedList(StringArrayList matchedList) {
		Search.matchedList = matchedList;
	}
}
