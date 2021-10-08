import React, {forwardRef} from 'react'
import ListItem from '@material-ui/core/ListItem'
import {NavLink, NavLinkProps} from 'react-router-dom'

export interface AppMenuItemComponentProps {
  className?: string
  link?: string | null
  onClick?: (event: React.MouseEvent<HTMLElement>) => void
}

const AppMenuItemComponent: React.FC<AppMenuItemComponentProps> = props => {
  const {className, onClick, link, children} = props

  if (link) {
    return (
      <ListItem
        button
        className={className}
        children={children}
        component={forwardRef((props: NavLinkProps, ref: any) => <NavLink exact {...props} innerRef={ref}/>)}
        to={link}
      />
    )
  }

  return (
    <ListItem
      button
      className={className}
      children={children}
      onClick={onClick}
    />
  )
}

export default AppMenuItemComponent
