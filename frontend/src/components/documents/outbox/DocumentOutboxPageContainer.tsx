import React, {useEffect, useState} from "react";
import DocumentOutboxPage, {IOutboxDocument} from "./DocumentOutboxPage";
import {request} from "../../../utils/requestUtils";
import {IDocumentModalOpen} from "../DocumentModal";

const DocumentOutboxPageContainer = () => {

  const [outboxDocuments, setOutboxDocuments] = useState<IOutboxDocument[]>([])
  const [documentModalOpen, setDocumentModalOpen] = useState<IDocumentModalOpen>({
    documentId: 0,
    open: false
  });

  const fetchOutboxDocuments = async () => {
    const {data: outboxDocuments} = await request.get('/api/documents/outbox?drafterId=1')
    setOutboxDocuments(outboxDocuments);
  }

  useEffect(() => {
    fetchOutboxDocuments();
  }, [])

  const props = {
    outboxDocuments,
    documentModalOpen,
    setDocumentModalOpen
  }

  return (
    <>
      <DocumentOutboxPage
        {...props}
      />
    </>
  );
}

export default DocumentOutboxPageContainer;
