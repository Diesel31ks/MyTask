package views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import org.eclipse.swt.widgets.Text;

/**
 * This is view for showing properties of text
 *
 * @author adm.hromov.os
 *
 */

public class Properties extends ViewPart {

	public static final String ID = "views.Properties"; //$NON-NLS-1$
	private Text textField;

	public Properties() {
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		{
			textField = new Text(container, SWT.BORDER | SWT.READ_ONLY | SWT.MULTI);
			textField.setEditable(true);
			textField.setBounds(10, 10, 370, 225);
		}
	}

	@Override
	public void setFocus() {
		textField.setFocus();
	}
	
	public String getTextFieldText() {
		return textField.getText();
	}

	public void setTextFieldText(String text) {
		this.textField.setText(text);
	}
}
