package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.monitoratec.loysci_android.DoneAvatarActivity;
import br.com.monitoratec.loysci_android.presentation.ui.adapters.AvatarAdapter;

import static br.com.monitoratec.loysci_android.presentation.ui.activities.AvatarActivity.BODY;
import static br.com.monitoratec.loysci_android.presentation.ui.activities.AvatarActivity.HAIR;
import static br.com.monitoratec.loysci_android.presentation.ui.activities.AvatarActivity.HAT;
import static br.com.monitoratec.loysci_android.presentation.ui.activities.AvatarActivity.HEAD;

public class AvatarPresenter {

    AvatarActivity activity;

    public AvatarPresenter(AvatarActivity activity) {
        this.activity = activity;
    }


    public Bitmap createSingleImageFromMultipleImages(Bitmap firstImage, Bitmap secondImage, Bitmap thirdImage, Bitmap fourthImage, Bitmap fifthImage, Bitmap background) {

        Bitmap result = Bitmap.createBitmap(firstImage.getWidth(), firstImage.getHeight(), firstImage.getConfig());
        Canvas canvas = new Canvas(result);

        canvas.drawBitmap(background, 0f, 0f, null);

        canvas.drawBitmap(firstImage, 0f, 0f, null);

        if (secondImage != null)
            canvas.drawBitmap(secondImage, 0f, 0f, null);

        if (thirdImage != null)
            canvas.drawBitmap(thirdImage, 0f, 0f, null);

        if (fourthImage != null)
            canvas.drawBitmap(fourthImage, 0f, 0f, null);

        if (fifthImage != null)
            canvas.drawBitmap(fifthImage, 0f, 0f, null);

        return result;
    }

    public List<Bitmap> getAllAvatarAssets(String path) {
        List<Bitmap> bitmapList = new ArrayList<>();

        try {
            AssetManager assetManager = activity.getAssets();
            String[] heads = assetManager.list(path);

            for (String s : heads) {
                bitmapList.add(BitmapFactory.decodeStream(assetManager.open(path + "/" + s)));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmapList;
    }

    public void saveAvatar(Bitmap bitmap, Integer[] options) {
//        try {
//            File path = new File(activity.getFilesDir().toString() + "/avatar/");
//
//            if(!path.exists()) {
//                path.mkdirs();
//            }
//            File file = new File(path, "avatar.jpg");
//            if (file.exists()){
//                file.delete();
//            }
//
//            if(!file.exists()) {
//                file.createNewFile();
//            }
//            FileOutputStream fOut = new FileOutputStream(file);
//
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
//            fOut.close();
//            Prefs.savePathAvatar(file.getAbsolutePath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Intent intent = new Intent();
        intent.putExtra("data", bitmap);
        activity.setResult(Activity.RESULT_OK, intent);

        Intent goToDoneAvatar = new Intent(activity, DoneAvatarActivity.class);
        goToDoneAvatar.putExtra("avatar", bitmap);
        activity.startActivity(goToDoneAvatar);
        activity.finish();
    }

    public void showAssetsOnAvatar(int position, AvatarAdapter.Type type) {
        if (type == AvatarAdapter.Type.BODY) {
            activity.options[BODY] = position;

            if (activity.gender.equals("F")) {
                activity.showAssetSelected(activity.femaleBodyBitmapList.get(position), activity.binding.ivAvatarBody);
            } else if (activity.gender.equals("M")) {
                activity.showAssetSelected(activity.maleBodyBitmapList.get(position), activity.binding.ivAvatarBody);
            }

        } else if (type == AvatarAdapter.Type.HAIR) {
            activity.options[HAIR] = position;

            if (activity.gender.equals("F")) {
                activity.showAssetSelected(activity.femaleHairBitmapList.get(position), activity.binding.ivAvatarHair);
            } else if (activity.gender.equals("M")) {
                activity.showAssetSelected(activity.maleHairBitmapList.get(position), activity.binding.ivAvatarHair);
            }
        } else if (type == AvatarAdapter.Type.HAT) {
            activity.options[HAT] = position;

            if (activity.gender.equals("F")) {
                activity.showAssetSelected(activity.femaleHatBitmapList.get(position), activity.binding.ivAvatarHat);
            } else if (activity.gender.equals("M")) {
                activity.showAssetSelected(activity.maleHatBitmapList.get(position), activity.binding.ivAvatarHat);
            }

        } else if (type == AvatarAdapter.Type.HEAD) {
            activity.options[HEAD] = position;
            if (activity.gender.equals("F")) {
                activity.showAssetSelected(activity.femaleHeadBitmapList.get(position), activity.binding.ivAvatarHead);
            } else if (activity.gender.equals("M")) {
                activity.showAssetSelected(activity.maleHeadBitmapList.get(position), activity.binding.ivAvatarHead);
            }
        }
    }
}
