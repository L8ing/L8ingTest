package ranker.action;

import java.util.Properties;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import ranker.View;
import ranker.dialog.FilterDialog;
import ranker.util.Utils;

public class ConfigItem extends ContributionItem {

	private static String id = "ranker.config";

	private static String LABEL = "ºó×º¹ýÂË";

	public ConfigItem() {
		this(id);
	}

	public ConfigItem(String id) {
		super(id);
	}

	public void fill(Menu menu, int index) {
		super.fill(menu, index);
		createMenu(menu, LABEL);
	}

	private void createMenu(Menu menu, String label) {

		MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
		menuItem.setText(label);
		menuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IWorkbench workbench = PlatformUI.getWorkbench();
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if (window == null) {
					return;
				}
				Shell shell = window.getShell();
				FilterDialog dialog = new FilterDialog(shell);
				int ret = dialog.open();
				if (ret == Window.OK) {
					Properties properties = Utils.loadPropertiesFromFile(Utils
							.getPropFile());
					properties.setProperty(View.FILTER, dialog.t);
					Utils.writeProperties2File(properties, Utils.getPropFile());
				}
				// File parentDir = new File(textFile);
				// View.setInput(parentDir);
			}
		});
	}

}
