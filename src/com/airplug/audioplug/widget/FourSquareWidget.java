/*
 * Copyright (C) 2010, 2011 Christopher Eby <kreed@kreed.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.airplug.audioplug.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

import com.airplug.audioplug.dev.R;
import com.airplug.audioplug.player.AudioFile;
import com.airplug.audioplug.player.IAudioService;
import com.airplug.audioplug.player.ServiceManager;

/**
 * 2x2 widget that shows title, artist, a (hidden) play/pause button, a (hidden)
 * next button, and cover art in the background.
 */
public class FourSquareWidget extends AppWidgetProvider {
	private static boolean sEnabled;
	private IAudioService service;

	@Override
	public void onEnabled(Context context)
	{
		sEnabled = true;
	}

	@Override
	public void onDisabled(Context context)
	{
		sEnabled = false;
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager manager, int[] ids)
	{
		AudioFile song = null;
		boolean state = false ;
		ServiceManager srvManager= new ServiceManager(context);
		service = srvManager.getService();
		if (service != null) {
//			PlaybackService service = PlaybackService.get(context);
			song = service.getAudioEntry();
			state = service.isPlaying();
		}

		sEnabled = true;
		updateWidget(context, manager, song, state);
	}

	/**
	 * Check if there are any instances of this widget placed.
	 */
	public static void checkEnabled(Context context, AppWidgetManager manager)
	{
		sEnabled = manager.getAppWidgetIds(new ComponentName(context, FourSquareWidget.class)).length != 0;
	}

	/**
	 * Populate the widgets with the given ids with the given info.
	 *
	 * @param context A Context to use.
	 * @param manager The AppWidgetManager that will be used to update the
	 * widget.
	 * @param song The current Song in PlaybackService.
	 * @param state The current PlaybackService state.
	 */
	public static void updateWidget(Context context, AppWidgetManager manager, AudioFile song, boolean state)
	{
		if (!sEnabled)
			return;

		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.four_square_widget);

//		boolean playing = (state & PlaybackService.FLAG_PLAYING) != 0;
//		int playResource = R.drawable.play;
//		int nextResource = R.drawable.next;
//		Bitmap cover = null;
//
//		if ((state & PlaybackService.FLAG_NO_MEDIA) != 0) {
//			views.setViewVisibility(R.id.buttons, View.INVISIBLE);
//			views.setViewVisibility(R.id.title, View.INVISIBLE);
//			views.setInt(R.id.artist, "setText", R.string.no_songs);
//		} else if (song == null) {
//			views.setViewVisibility(R.id.buttons, View.VISIBLE);
//			views.setViewVisibility(R.id.title, View.INVISIBLE);
//			views.setInt(R.id.artist, "setText", R.string.app_name);
//		} else {
//			views.setViewVisibility(R.id.title, View.VISIBLE);
//			views.setViewVisibility(R.id.buttons, View.VISIBLE);
//			views.setTextViewText(R.id.title, song.title);
//			views.setTextViewText(R.id.artist, song.artist);
//			cover = song.getCover(context);
//			playResource = playing ? R.drawable.hidden_pause : R.drawable.hidden_play;
//			nextResource = R.drawable.hidden_next;
//		}
//
//		views.setImageViewResource(R.id.play_pause, playResource);
//		views.setImageViewResource(R.id.next, nextResource);
//
//		if (cover == null) {
//			views.setImageViewResource(R.id.cover, R.drawable.fallback_cover);
//		} else {
//			views.setImageViewBitmap(R.id.cover, cover);
//		}
//
//
//		Intent intent;
//		PendingIntent pendingIntent;
//
//		ComponentName service = new ComponentName(context, PlaybackService.class);
//
//		intent = new Intent(context, LibraryActivity.class);
//		intent.setAction(Intent.ACTION_MAIN);
//		pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//		views.setOnClickPendingIntent(R.id.title, pendingIntent);
//		views.setOnClickPendingIntent(R.id.artist, pendingIntent);
//
//		intent = new Intent(PlaybackService.ACTION_TOGGLE_PLAYBACK).setComponent(service);
//		pendingIntent = PendingIntent.getService(context, 0, intent, 0);
//		views.setOnClickPendingIntent(R.id.play_pause, pendingIntent);
//
//		intent = new Intent(PlaybackService.ACTION_NEXT_SONG).setComponent(service);
//		pendingIntent = PendingIntent.getService(context, 0, intent, 0);
//		views.setOnClickPendingIntent(R.id.next, pendingIntent);
//
		manager.updateAppWidget(new ComponentName(context, FourSquareWidget.class), views);
	}
}
