package com.p_alex.happinesstracker;

import android.database.Cursor;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public abstract class ApplicationFragment extends Fragment {
    public abstract void updateBackgroundHappiness();
    public abstract RelativeLayout getRelativeLayout();

    public static int calculateHappiness(Cursor cursor) {
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Double countSadSmiles = 0.0, countNormalSmiles = 0.0, countHappySmiles = 0.0;

            while (!cursor.isLast()) {
                switch (cursor.getInt(1)) {
                    case TableInformation.Table.SAMPLE_VALUE_SAD:
                        countSadSmiles++;
                        break;
                    case TableInformation.Table.SAMPLE_VALUE_NORMAL:
                        countNormalSmiles++;
                        break;
                    case TableInformation.Table.SAMPLE_VALUE_HAPPY:
                        countHappySmiles++;
                        break;
                }

                cursor.moveToNext();
            }

            Double max = Math.max(countSadSmiles, Math.max(countNormalSmiles, countHappySmiles));

            if (Double.compare(max, countSadSmiles) == 0) {
                return TableInformation.Table.SAMPLE_VALUE_SAD;
            }

            if (Double.compare(max, countNormalSmiles) == 0) {
                return TableInformation.Table.SAMPLE_VALUE_NORMAL;
            }

            if (Double.compare(max, countHappySmiles) == 0) {
                return TableInformation.Table.SAMPLE_VALUE_HAPPY;
            }
        }

        return 0;
    }

    public void updateBackgroundColor(int happinessType) {
        RelativeLayout relativeLayout = getRelativeLayout();

        switch (happinessType) {
            case 1:
                relativeLayout.setBackgroundColor(getResources().getColor(R.color.color_sad));
                break;
            case 2:
                relativeLayout.setBackgroundColor(getResources().getColor(R.color.color_normal));
                break;
            default:
                relativeLayout.setBackgroundColor(getResources().getColor(R.color.color_happy));
                break;
        }
    }

    public void updateBackgroundImage(int happinessType) {
        RelativeLayout relativeLayout = getRelativeLayout();
        ImageView image = (ImageView) relativeLayout.findViewById(R.id.happiness_image);

        switch (happinessType) {
            case 1:
                image.setImageResource(R.drawable.smile_sad_big);
                break;
            case 2:
                image.setImageResource(R.drawable.smile_normal_big);
                break;
            default:
                image.setImageResource(R.drawable.smile_happy_big);
                break;
        }
    }
}
