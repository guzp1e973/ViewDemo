package com.yeepay.myrefresh;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RefreshView extends LinearLayout implements OnTouchListener {
	private View header;
	private ProgressBar progressBar;
	private TextView description;
	private boolean loadOnce = false;
	private MarginLayoutParams headerLayoutParams;
	private int hideHeaderHeight;
	private ListView listView;
	private boolean ableToPull;
	private float yDown;
	private int currentStatus=-1;
	private PullToRefreshListener mListener;
	public static final int SCROLL_SPEED = -20;
	public static final int STATUS_PULL_TO_REFRESH = 0;
	public static final int STATUS_RELEASE_TO_REFRESH = 1;
	public static final int STATUS_REFRESHING = 2;
	public static final int STATUS_REFRESH_FINISHED = 3;
	public RefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		header = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh,
				null, true);
		progressBar = (ProgressBar) header.findViewById(R.id.progress_bar);
		description = (TextView) header.findViewById(R.id.description);
		// touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		setOrientation(VERTICAL);
		addView(header, 0);
	}
	
	/**
	 * 初始化操作，只需在加载时执行一次
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		if (changed && !loadOnce) {
			hideHeaderHeight = -header.getHeight();
			headerLayoutParams = (MarginLayoutParams) header.getLayoutParams();
			headerLayoutParams.topMargin = hideHeaderHeight;
			listView = (ListView) getChildAt(1);
			 listView.setOnTouchListener(this);
			loadOnce = true;
		}
	}

	private void setIsAbleToPull(MotionEvent event) {
		View firstChild = listView.getChildAt(0);
		if (firstChild != null) {
			int firstVisiblePos = listView.getFirstVisiblePosition();
			if (firstVisiblePos == 0 && firstChild.getTop() == 0) {
				// 如果首个元素的上边缘，距离父布局值为0，就说明ListView滚动到了最顶部，此时应该允许下拉刷新

				ableToPull = true;
			} else {
				ableToPull = false;
			}
		} else {
			// 如果ListView中没有元素，也应该允许下拉刷新
			ableToPull = true;
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		setIsAbleToPull(event);
		if (ableToPull) {
			description.setText("下拉刷新");
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				yDown = event.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				float yMove = event.getRawY();
				int distance = (int) (yMove - yDown);
				// 如果手指是上滑状态，并且下拉头是完全隐藏的，就屏蔽下拉事件
				//Log.i("msg", "distance:"+distance);
				if (distance <= 0
						&& headerLayoutParams.topMargin <= hideHeaderHeight) {
					return false;
				}

				if (currentStatus != STATUS_REFRESHING) {
					if (headerLayoutParams.topMargin > 0) {
						currentStatus = STATUS_RELEASE_TO_REFRESH;
					} else {
						currentStatus = STATUS_PULL_TO_REFRESH;
					}
					// 通过偏移下拉头的topMargin值，来实现下拉效果

					headerLayoutParams.topMargin = (distance / 2)
							+ hideHeaderHeight;
					header.setLayoutParams(headerLayoutParams);
				}
				break;
			case MotionEvent.ACTION_UP:
			
				if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
					// 松手时如果是释放立即刷新状态，就去调用正在刷新的任务

					new RefreshingTask().execute();
				} else if (currentStatus == STATUS_PULL_TO_REFRESH) {
					// 松手时如果是下拉状态，就去调用隐藏下拉头的任务

					new HideHeaderTask().execute();
				}
				break;
			default:
			}

			if (currentStatus == STATUS_PULL_TO_REFRESH
					|| currentStatus == STATUS_RELEASE_TO_REFRESH) {
				// 当前正处于下拉或释放状态，要让ListView失去焦点，否则被点击的那一项会一直处于选中状态
				
				listView.setPressed(false);
				listView.setFocusable(false);
				listView.setFocusableInTouchMode(false);
				// 当前正处于下拉或释放状态，通过返回true屏蔽掉ListView的滚动事件
				return true;
			}
		}
		return false;
	}
	
	class RefreshingTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			int topMargin = headerLayoutParams.topMargin;
			while (true) {
				topMargin = topMargin + SCROLL_SPEED;
				if (topMargin <= 0) {
					topMargin = 0;
					break;
				}
				publishProgress(topMargin);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			currentStatus = STATUS_REFRESHING;
			publishProgress(0);
			if (mListener != null) {
				mListener.onRefresh();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... topMargin) {
			description.setText("正在刷新。。");
			headerLayoutParams.topMargin = topMargin[0];
			header.setLayoutParams(headerLayoutParams);
		}
		

	}

	/**
	 * 隐藏下拉头的任务，当未进行下拉刷新或下拉刷新完成后，此任务将会使下拉头重新隐藏。
	 * 
	 * @author guolin
	 */
	class HideHeaderTask extends AsyncTask<Void, Integer, Integer> {

		

		@Override
		protected Integer doInBackground(Void... params) {
			int topMargin = headerLayoutParams.topMargin;
			while (true) {
				topMargin = topMargin + SCROLL_SPEED;
				if (topMargin <= hideHeaderHeight) {
					topMargin = hideHeaderHeight;
					break;
				}
				publishProgress(topMargin);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return topMargin;
		}

		@Override
		protected void onProgressUpdate(Integer... topMargin) {
			headerLayoutParams.topMargin = topMargin[0];
			header.setLayoutParams(headerLayoutParams);
		}

		@Override
		protected void onPostExecute(Integer topMargin) {
			headerLayoutParams.topMargin = topMargin;
			header.setLayoutParams(headerLayoutParams);
			currentStatus = STATUS_REFRESH_FINISHED;
		}
	}
	public void setOnRefreshListener(PullToRefreshListener listener) {
		mListener = listener;
	}

	/**
	 * 当所有的刷新逻辑完成后，调用一下，否则ListView将一直处于正在刷新状态。
	 */
	public void finishRefreshing() {
		currentStatus = STATUS_REFRESH_FINISHED;
		new HideHeaderTask().execute();
	}

	public interface PullToRefreshListener {

		/**
		 * 刷新时会去回调此方法，在方法内编写具体的刷新逻辑。
		 */
		void onRefresh();

	}
}
