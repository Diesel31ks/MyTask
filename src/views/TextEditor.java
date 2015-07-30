package views;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import mytask.Perspective;
import mytask.StringArrayList;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

/**
 * This is main view
 * 
 * @author adm.hromov.os
 *
 */
public class TextEditor extends ViewPart {
	public static final String ID = "views.View";
	private static StringArrayList strings = new StringArrayList();
	private Text textField;

	public TextEditor() {
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		{
			textField = new Text(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
			textField.setEditable(false);
			textField.setSize(370, 449);
			textField.setLocation(10, 10);
			textField.addModifyListener(new ModifyListener() {
				
				private Properties properties = (Properties) Perspective
						.getView(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), Properties.ID);
				
				private int textLineCount;
				private int textWordCount;
				private int textSymvolsCount;
				
				public void modifyText(ModifyEvent e) {
					fillStrings();
					calculationProperties();
					updateProperties();
				}
				
				private void fillStrings() {
					strings.clear();
					String[] array = getTextField().getText().split("\n");
					for (int i=0;i<array.length-1;i++) {
						strings.add(array[i]);
					}
				}
				
				private void calculationProperties() {
					setTextLineCount(textField.getLineCount());
					int wordCount = strings.toString().split(" ").length;
					setTextWordCount(wordCount);
					setTextSymvolsCount(textField.getText().length());
				}
				
				private void updateProperties() {
					StringBuilder builder = new StringBuilder().append("Text line's count = " + getTextLineCount() + ";\n");
					builder.append("Text word's count = " + getTextWordCount() + ";\n");
					builder.append("Text symbol's count = " + getTextSymvolsCount() + ";\n");
					properties.setTextFieldText(builder.toString());
				}
				
				public int getTextLineCount() {
					return textLineCount;
				}

				public void setTextLineCount(int textLineCount) {
					this.textLineCount = textLineCount;
				}
				
				public int getTextWordCount() {
					return textWordCount;
				}

				public void setTextWordCount(int textWordCount) {
					this.textWordCount = textWordCount;
				}

				public int getTextSymvolsCount() {
					return textSymvolsCount;
				}

				public void setTextSymvolsCount(int textSymvolsCount) {
					this.textSymvolsCount = textSymvolsCount;
				}
			});
		}
	}

	@Override
	public void setFocus() {
		textField.setFocus();
	}

	public void setStrings(List<String> strings) {
		TextEditor.strings = (StringArrayList) strings;
	}

	public StringArrayList getStrings() {
		return strings;
	}

	public Text getTextField() {
		return textField;
	}
}
