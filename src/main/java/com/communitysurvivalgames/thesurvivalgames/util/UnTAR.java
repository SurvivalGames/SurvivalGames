package com.communitysurvivalgames.thesurvivalgames.util;


public class UnTAR {
    public static List<File> unTar(final File inputFile, final File outputDir)
            throws FileNotFoundException, IOException, ArchiveException {
        final List<File> untaredFiles = new LinkedList<File>();
        final InputStream is = new FileInputStream(inputFile);
        final TarArchiveInputStream debInputStream = (TarArchiveInputStream) new ArchiveStreamFactory()
                .createArchiveInputStream("tar", is);
        TarArchiveEntry entry = null;
        while ((entry = (TarArchiveEntry) debInputStream.getNextEntry()) != null) {
            final File outputFile = new File(outputDir, entry.getName());
            if (entry.isDirectory()) {

                if (!outputFile.exists()) {

                    if (!outputFile.mkdirs()) {
                        throw new IllegalStateException(String.format(
                                "Failed to create directory %s.",
                                outputFile.getAbsolutePath()));
                    }
                }
            } else {
                final OutputStream outputFileStream = new FileOutputStream(
                        outputFile);
                IOUtils.copy(debInputStream, outputFileStream);
                outputFileStream.close();
            }
            untaredFiles.add(outputFile);
        }
        debInputStream.close();
        return untaredFiles;
    }
}
