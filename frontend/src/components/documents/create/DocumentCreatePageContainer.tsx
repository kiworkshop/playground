import React, {useEffect, useState} from "react";
import DocumentCreatePage, {IApprover, IDocumentCategorySelectItem, IDocumentPageParams} from "./DocumentCreatePage";
import {request} from "../../../utils/requestUtils";
import { useHistory } from 'react-router-dom';

const DocumentCreatePageContainer = () => {

  const history = useHistory();
  const [params, setParams] = useState<IDocumentPageParams>({
    category: '',
    title: '',
    contents: '',
  })
  const [categorySelectItems, setCategorySelectItems] = useState<IDocumentCategorySelectItem[]>([])
  const [approverSelectModalOpen, setApproverSelectModalOpen] = useState(false);
  const [approvers, setApprovers] = useState<IApprover[]>([]);

  const fetchCategorySelectItems = async () => {
    const {data: categoryItems} = await request.get('/api/documents/categories')

    setCategorySelectItems(categoryItems)
  }

  const onConfirm = async () => {
    const approverIds = approvers.map(item => item.id)

    await request.post('/api/documents', {
      ...params,
      drafterId: 1,
      approverIds
    })
    history.push("/documents/outbox");
  }

  useEffect(() => {
    fetchCategorySelectItems();
  }, [])

  const props = {
    params,
    setParams,
    approvers,
    setApprovers,
    categorySelectItems,
    onConfirm,
    approverSelectModalOpen,
    setApproverSelectModalOpen,
  }

  return (
    <DocumentCreatePage
      {...props}
    />
  );
}

export default DocumentCreatePageContainer;
