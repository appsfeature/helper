package android.print;

import android.os.Build;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.util.Log;


import java.io.File;

public class PdfPrint {

    private static final String TAG = PdfPrint.class.getSimpleName();
    private final PrintAttributes printAttributes;

    public PdfPrint(PrintAttributes printAttributes) {
        this.printAttributes = printAttributes;
    }


    //define callback interface
    public interface PDFSaveInterface {
        void onSaveFinished(boolean isSuccess);
    }

    public void print(final PrintDocumentAdapter printAdapter, final File path, final String fileName, final PDFSaveInterface callback) {
        if(Build.VERSION.SDK_INT >= 19) {
            printAdapter.onLayout(null, printAttributes, null, new PrintDocumentAdapter.LayoutResultCallback() {
                @Override
                public void onLayoutFinished(PrintDocumentInfo info, boolean changed) {
                    if(Build.VERSION.SDK_INT >= 19) {
                        if(getOutputFile(path, fileName) != null) {
                            printAdapter.onWrite(new PageRange[]{PageRange.ALL_PAGES}, getOutputFile(path, fileName), new CancellationSignal(), new PrintDocumentAdapter.WriteResultCallback() {
                                @Override
                                public void onWriteFinished(PageRange[] pages) {
                                    super.onWriteFinished(pages);
                                    callback.onSaveFinished(pages != null && pages.length > 0);
                                }
                            });
                        } else {
                            callback.onSaveFinished(false);
                        }
                    }
                }
            }, null);
        }
    }

    private ParcelFileDescriptor getOutputFile(File path, String fileName) {
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, fileName);
        try {
            file.createNewFile();
            return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_WRITE);
        } catch (Exception e) {
            Log.e(TAG, "Failed to open ParcelFileDescriptor : " + e.getMessage());
        }
        return null;
    }
}