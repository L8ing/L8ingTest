package test.srt.player;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import test.srt.player.utils.SrtBody;
import test.srt.player.utils.SrtFileParser;

public class View extends ViewPart {

	public static final String ID = "test.srt.player.view";

	private Display display;

	private StyledText styledText;

	private List<SrtBody> content;

	private boolean isStart = false;

	/**
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */
	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			if (parent instanceof Object[]) {
				return (Object[]) parent;
			}
			return new Object[0];
		}
	}

	class ViewLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}

		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}

		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().getSharedImages()
					.getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(final Composite parent) {
		display = parent.getDisplay();
		parent.setLayout(new GridLayout(3, false));
		{
			Composite buttonComposite = new Composite(parent, SWT.NONE);
			buttonComposite.setLayout(new GridLayout(1, false));
			buttonComposite.setLayoutData(new GridData(GridData.FILL_VERTICAL));
			Button importButton = new Button(buttonComposite, SWT.PUSH);
			importButton.setText("import");
			importButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			importButton.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event arg0) {
					FileDialog dialog = new FileDialog(parent.getShell(),
							SWT.OPEN | SWT.SINGLE);
					dialog.setText("选择文件");
					File[] files = File.listRoots();
					if (files.length > 0) {
						dialog.setFilterPath(files[0].getAbsolutePath());
					}
					String textFile = dialog.open();
					if (textFile == null) {
						return;
					}
					try {
						content = SrtFileParser.parse(textFile);
					} catch (IOException | ParseException e) {
						MessageDialog.openError(parent.getShell(), "字幕文件解析出错",
								e.getMessage());
						return;
					}
				}
			});
			Button runButton = new Button(buttonComposite, SWT.PUSH);
			runButton.setText("run");
			runButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			runButton.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event arg0) {
					if (!isStart) {
						if (content == null) {
							MessageDialog.openError(parent.getShell(), "错误",
									"未指定字幕文件");
							return;
						}
						isStart = true;
						Timer timer = new Timer();
						ManageTask task = new ManageTask(0);
						timer.schedule(task, 0);
					}
				}
			});

			Button stopButton = new Button(buttonComposite, SWT.PUSH);
			stopButton.setText("stop");
			stopButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			stopButton.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event arg0) {
					if (isStart) {
						isStart = false;
					}
				}
			});
		}

		{
			Composite jumpComposite = new Composite(parent, SWT.NONE);
			jumpComposite.setLayout(new GridLayout(1, false));
			jumpComposite.setLayoutData(new GridData(GridData.FILL_VERTICAL));
			Composite timeComposite = new Composite(jumpComposite, SWT.NONE);
			timeComposite.setLayout(new GridLayout(5, false));
			timeComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			final Spinner hourSpinner = new Spinner(timeComposite, SWT.NONE);
			hourSpinner.setMaximum(59);
			hourSpinner.setMinimum(0);
			new Label(timeComposite, SWT.NONE).setText(":");
			final Spinner minuteSpinner = new Spinner(timeComposite, SWT.NONE);
			minuteSpinner.setMaximum(59);
			minuteSpinner.setMinimum(0);
			new Label(timeComposite, SWT.NONE).setText(":");
			final Spinner secondSpinner = new Spinner(timeComposite, SWT.NONE);
			secondSpinner.setMaximum(59);
			secondSpinner.setMinimum(0);

			Button jumpButton = new Button(jumpComposite, SWT.PUSH);
			jumpButton.setText("jump");
			jumpButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			jumpButton.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event arg0) {
					if (isStart) {
						MessageDialog.openError(parent.getShell(), "错误",
								"请先停止播放");
						return;
					} else {
						if (content == null) {
							MessageDialog.openError(parent.getShell(), "错误",
									"未指定字幕文件");
							return;
						}
						Calendar c = Calendar.getInstance();
						int hour = Integer.parseInt(hourSpinner.getText());
						int minute = Integer.parseInt(minuteSpinner.getText());
						int second = Integer.parseInt(secondSpinner.getText());
						c.set(1970, 0, 1, hour, minute, second);
						Date jumpTo = c.getTime();
						int index = -1;
						for (SrtBody srtbody : content) {
							Date start = srtbody.getStart();
							if (start.after(jumpTo)) {
								break;
							}
							index = srtbody.getIndex();
						}
						if (index >= 0) {
							isStart = true;
							Timer timer = new Timer();
							ManageTask task = new ManageTask(index);
							timer.schedule(task, 0);
						}
					}
				}
			});
		}
		styledText = new StyledText(parent, SWT.NONE);
		styledText.setFont(new Font(parent.getDisplay(), "宋体", 18, SWT.BOLD));
		styledText.setEditable(false);
		styledText.setLayoutData(new GridData(GridData.FILL_BOTH));
		// OSUtil.setShellAlpha(parent.getShell(), 100);
	}

	class ManageTask extends TimerTask {

		private int index;

		public ManageTask(int index) {
			this.index = index;
		}

		public void run() {

			Calendar c = Calendar.getInstance();
			c.set(1970, 0, 1, 0, 0, 0);
			long origionalTime = c.getTime().getTime();

			if (index > 0) {
				origionalTime = content.get(index).getStart().getTime();
			}

			for (int i = index; i < content.size(); i++) {
				SrtBody srtbody = content.get(i);
				if (!isStart) {
					if (!display.isDisposed()) {
						Runnable runnable = new Runnable() {
							public void run() {
								styledText.setText("");
							}
						};
						display.asyncExec(runnable);
					}
					break;
				}
				Date start = srtbody.getStart();
				long startTime = start.getTime();
				long endTime = srtbody.getEnd().getTime();
				final String text = srtbody.getText();
				long t1 = startTime - origionalTime;
				try {
					Thread.sleep(t1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (!display.isDisposed()) {
					Runnable runnable = new Runnable() {
						public void run() {
							styledText.setText(text);
						}
					};
					display.asyncExec(runnable);
				}

				long t2 = endTime - startTime;
				try {
					Thread.sleep(t2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				origionalTime = endTime;
			}

			if (!display.isDisposed()) {
				Runnable runnable = new Runnable() {
					public void run() {
						styledText.setText("");
					}
				};
				display.asyncExec(runnable);
			}

		}
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}
}