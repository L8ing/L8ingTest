package test.srt.player;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import test.srt.player.utils.SrtBody;
import test.srt.player.utils.SrtFileParser;

public class View extends ViewPart {

	public static final String ID = "test.srt.player.view";

	private Display display;

	private StyledText styledText;

	private List<SrtBody> content;

	private ManageTask task;

	private Timer timer = new Timer();

	private Text timeText;

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(final Composite parent) {
		display = parent.getDisplay();
		parent.setLayout(new GridLayout(2, false));

		Composite panelComposite = new Composite(parent, SWT.NONE);
		panelComposite.setLayout(new GridLayout(1, false));
		{
			Composite buttonComposite = new Composite(panelComposite, SWT.NONE);
			buttonComposite.setLayout(new GridLayout(5, false));
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
			runButton.setText("play");
			runButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			runButton.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event arg0) {
					if (task == null || task.isStop()) {
						if (content == null) {
							MessageDialog.openError(parent.getShell(), "错误",
									"未指定字幕文件");
							return;
						}
						task = new ManageTask(0);
						task.setPlay();
						timer.schedule(task, 0);
					} else if (task.isPause()) {
						if (task != null) {
							task.setPlay();
							synchronized (task) {
								task.notifyAll();
							}
						}
					}
				}
			});

			Button pauseButton = new Button(buttonComposite, SWT.PUSH);
			pauseButton.setText("pause");
			pauseButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			pauseButton.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event arg0) {
					if (task != null && task.isPlay()) {
						task.setPause();
					}
				}
			});

			Button stopButton = new Button(buttonComposite, SWT.PUSH);
			stopButton.setText("stop");
			stopButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			stopButton.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event arg0) {
					if (task != null && task.isPlay()) {
						task.setStop();
					}
				}
			});
			
			timeText = new Text(buttonComposite, SWT.BORDER);
			timeText.setEditable(false);

		}

		{
			Composite jumpComposite = new Composite(panelComposite, SWT.NONE);
			jumpComposite.setLayout(new GridLayout(2, false));
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
					if (task != null && (task.isPlay() || task.isPause())) {
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
							Timer timer = new Timer();
							task = new ManageTask(index);
							task.setPlay();
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

		/**
		 * -1:停止；0：播放；1：暂停
		 */
		private int status = -1;

		private int index;

		public ManageTask(int index) {
			this.index = index;
		}

		public boolean isPlay() {
			if (status == 0) {
				return true;
			} else {
				return false;
			}
		}

		public boolean isPause() {
			if (status == 1) {
				return true;
			} else {
				return false;
			}
		}

		public boolean isStop() {
			if (status == -1) {
				return true;
			} else {
				return false;
			}
		}

		public void setPlay() {
			status = 0;
		}

		public void setPause() {
			status = 1;
		}

		public void setStop() {
			status = -1;
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
				if (status == -1) {
					if (!display.isDisposed()) {
						Runnable runnable = new Runnable() {
							public void run() {
								timeText.setText("");
								styledText.setText("");
							}
						};
						display.asyncExec(runnable);
					}
					break;
				} else if (status == 1) {
					synchronized (this) {
						try {
							wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				Date start = srtbody.getStart();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				final String time = sdf.format(start);
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
							timeText.setText(time);
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

				if (!display.isDisposed()) {
					Runnable runnable = new Runnable() {
						public void run() {
							timeText.setText("");
							styledText.setText("");
						}
					};
					display.asyncExec(runnable);
				}
			}

		}
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}
}