package top.trumeet.common.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.util.Log;
import android.util.LruCache;

import top.trumeet.common.utils.ImgUtils;

import static top.trumeet.common.utils.ImgUtils.drawableToBitmap;

/**
 * Author: TimothyZhang023
 * Icon Cache
 * 
 * Code implements port from
 * https://github.com/MiPushFramework/MiPushFramework
 */
public class IconCache {

    private volatile static IconCache cache = null;
    private LruCache<String, Bitmap> bitmapLruCache;
    private LruCache<String, Icon> mIconMemoryCaches;


    private LruCache<String, Integer> appColorCache;

    private IconCache() {
        bitmapLruCache = new LruCache<>(100);
        mIconMemoryCaches = new LruCache<>(100);
        appColorCache = new LruCache<>(100);
        //TODO check cacheSizes is correct ?
    }

    public static IconCache getInstance() {
        if (cache == null) {
            synchronized (IconCache.class) {
                if (cache == null) {
                    cache = new IconCache();
                }
            }
        }
        return cache;
    }

    public Bitmap getRawIconBitmapWithoutLoader(final Context ctx, final String pkg) {
        return bitmapLruCache.get("raw_" + pkg);
    }

    public Bitmap getRawIconBitmap(final Context ctx, final String pkg) {
        return new AbstractCacheAspect<Bitmap>(bitmapLruCache) {
            @Override
            Bitmap gen() {
                try {
					Log.d("inspect", "context " + ctx);
					Drawable icon = ctx.getPackageManager().getApplicationIcon(pkg);
					Log.d("inspect", "icon " + icon);
                    Bitmap r = drawableToBitmap(icon);
					Log.d("inspect", "bitmap " + r);
					return r;
                } catch (IllegalArgumentException ignored) {
					Log.d("inspect", "ooo " + pkg);
					return null;
                } catch (Exception ignored) {
                    return null;
                }
            }
        }.get("raw_" + pkg);
    }

    public Icon getIconCache(final Context ctx, final String pkg, Converter<Bitmap, Icon> callback) {
        return new AbstractCacheAspect<Icon>(mIconMemoryCaches) {
            @Override
            Icon gen() {
                Bitmap rawIconBitmap = getRawIconBitmap(ctx, pkg);
                Bitmap whiteIconBitmap = new WhiteIconProcess().convert(ctx, rawIconBitmap);
                return callback.convert(ctx, whiteIconBitmap);
            }
        }.get("white_" + pkg);
    }

    public int getAppColor(final Context ctx, final String pkg, Converter<Bitmap, Integer> callback) {
        return new AbstractCacheAspect<Integer>(appColorCache) {
            @Override
            Integer gen() {
                Bitmap rawIconBitmap = getRawIconBitmap(ctx, pkg);
                if (rawIconBitmap == null) {
                    return -1;
                }
                return callback.convert(ctx, rawIconBitmap);
            }
        }.get(pkg);
    }

    public static class WhiteIconProcess implements Converter<Bitmap, Bitmap> {
        @Override
        public Bitmap convert(Context ctx, Bitmap b) {
            if (b == null) {
                return null;
            }

            //scaleImage to 64dp
            int dip2px = dip2px(ctx, 64);
            return ImgUtils.scaleImage(ImgUtils.convertToTransparentAndWhite(b), dip2px, dip2px);
        }
    }

    public interface Converter<T,R> {
        R convert(Context ctx, T b);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
