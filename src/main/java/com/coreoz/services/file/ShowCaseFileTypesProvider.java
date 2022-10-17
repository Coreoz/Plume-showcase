package com.coreoz.services.file;

import com.coreoz.plume.file.filetype.FileTypeDatabase;
import com.coreoz.plume.file.filetype.FileTypesProvider;

import java.util.Collection;
import java.util.List;

public class ShowCaseFileTypesProvider implements FileTypesProvider {
    @Override
    public Collection<FileTypeDatabase> fileTypesAvailable() {
        return List.of(ShowcaseFileType.values());
    }
}
