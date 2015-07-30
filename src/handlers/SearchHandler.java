package handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.handlers.HandlerUtil;

import mytask.MyTitleAreaDialog;

/**
 * This handler is used for searching/replacing some text in text field in TextEditor view
 * 
 * @author adm.hromov.os
 *
 */
public class SearchHandler implements IHandler {
	private static MyTitleAreaDialog dialog; 
	
	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (dialog==null)
			dialog = new MyTitleAreaDialog(HandlerUtil.getActiveWorkbenchWindow(event).getShell());
		dialog.create();
		if (dialog.open() == Window.OK) {
		  System.out.println(dialog.getFind());
		  System.out.println(dialog.getReplace());
		} 
		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
	}
}
