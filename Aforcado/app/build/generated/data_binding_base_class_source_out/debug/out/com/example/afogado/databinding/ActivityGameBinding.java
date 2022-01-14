// Generated by view binder compiler. Do not edit!
package com.example.afogado.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.afogado.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityGameBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final GridView gridLetters;

  @NonNull
  public final ImageView ivAfogado;

  @NonNull
  public final ImageView ivHelp;

  @NonNull
  public final LinearLayout layoutPoints;

  @NonNull
  public final LinearLayout linearWords;

  @NonNull
  public final TextView tvMaxScore;

  @NonNull
  public final TextView tvPoints;

  @NonNull
  public final TextView tvTimer;

  private ActivityGameBinding(@NonNull LinearLayout rootView, @NonNull GridView gridLetters,
      @NonNull ImageView ivAfogado, @NonNull ImageView ivHelp, @NonNull LinearLayout layoutPoints,
      @NonNull LinearLayout linearWords, @NonNull TextView tvMaxScore, @NonNull TextView tvPoints,
      @NonNull TextView tvTimer) {
    this.rootView = rootView;
    this.gridLetters = gridLetters;
    this.ivAfogado = ivAfogado;
    this.ivHelp = ivHelp;
    this.layoutPoints = layoutPoints;
    this.linearWords = linearWords;
    this.tvMaxScore = tvMaxScore;
    this.tvPoints = tvPoints;
    this.tvTimer = tvTimer;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityGameBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityGameBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_game, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityGameBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.grid_letters;
      GridView gridLetters = rootView.findViewById(id);
      if (gridLetters == null) {
        break missingId;
      }

      id = R.id.iv_afogado;
      ImageView ivAfogado = rootView.findViewById(id);
      if (ivAfogado == null) {
        break missingId;
      }

      id = R.id.iv_help;
      ImageView ivHelp = rootView.findViewById(id);
      if (ivHelp == null) {
        break missingId;
      }

      id = R.id.layout_points;
      LinearLayout layoutPoints = rootView.findViewById(id);
      if (layoutPoints == null) {
        break missingId;
      }

      id = R.id.linear_words;
      LinearLayout linearWords = rootView.findViewById(id);
      if (linearWords == null) {
        break missingId;
      }

      id = R.id.tv_max_Score;
      TextView tvMaxScore = rootView.findViewById(id);
      if (tvMaxScore == null) {
        break missingId;
      }

      id = R.id.tv_points;
      TextView tvPoints = rootView.findViewById(id);
      if (tvPoints == null) {
        break missingId;
      }

      id = R.id.tv_timer;
      TextView tvTimer = rootView.findViewById(id);
      if (tvTimer == null) {
        break missingId;
      }

      return new ActivityGameBinding((LinearLayout) rootView, gridLetters, ivAfogado, ivHelp,
          layoutPoints, linearWords, tvMaxScore, tvPoints, tvTimer);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
