declare module 'downloadjs' {
  const download: (data: any, filename?: string, mimeType?: string) => void;
  export default download;
}

declare module 'file-saver' {
  const saveAs: (
    data: Blob | File | Url,
    filename?: DOMString,
    autoBom?: object,
  ) => void;
  export default saveAs;
}
