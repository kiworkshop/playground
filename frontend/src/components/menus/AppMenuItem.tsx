import React from 'react'
import PropTypes from 'prop-types'
import {createStyles, makeStyles, Theme} from '@material-ui/core/styles'
import List from '@material-ui/core/List'
import ListItemText from '@material-ui/core/ListItemText'
import Divider from '@material-ui/core/Divider'
import Collapse from '@material-ui/core/Collapse'

import IconExpandLess from '@material-ui/icons/ExpandLess'
import IconExpandMore from '@material-ui/icons/ExpandMore'

import AppMenuItemComponent from './AppMenuItemComponent'
import {ListItemIcon} from "@material-ui/core";

export const AppMenuItemPropTypes = {
  name: PropTypes.string.isRequired,
  link: PropTypes.string,
  items: PropTypes.array,
  top: PropTypes.bool,
  icon: PropTypes.element
}

// eslint-disable-next-line @typescript-eslint/no-redeclare
type AppMenuItemPropTypes = PropTypes.InferProps<typeof AppMenuItemPropTypes>
type AppMenuItemPropsWithoutItems = Omit<AppMenuItemPropTypes, 'items'>

export type AppMenuItemProps = AppMenuItemPropsWithoutItems & {
  items?: AppMenuItemProps[]
}

const AppMenuItem = (props: AppMenuItemProps) => {
  const {name, link, icon, items = []} = props
  const classes = useStyles(props)
  const isExpandable = items && items.length > 0
  const [open, setOpen] = React.useState(false)

  function handleClick() {
    setOpen(!open)
  }

  const MenuItemRoot = (
    <AppMenuItemComponent className={classes.menuItem} link={link} onClick={handleClick}>
      <ListItemIcon>
        {icon}
      </ListItemIcon>
      <ListItemText primary={name}/>
      {isExpandable && !open && <IconExpandMore/>}
      {isExpandable && open && <IconExpandLess/>}
    </AppMenuItemComponent>
  )

  const MenuItemChildren = isExpandable ? (
    <Collapse in={open} timeout="auto" unmountOnExit>
      <Divider/>
      <List component="div" disablePadding>
        {items.map((item, index) => (
          <AppMenuItem {...item} key={index} top={false}/>
        ))}
      </List>
    </Collapse>
  ) : null

  return (
    <>
      {MenuItemRoot}
      {MenuItemChildren}
    </>
  )
}

const useStyles = makeStyles<Theme, AppMenuItemProps>(theme =>
  createStyles({
    menuItem: {
      '&.active': {
        background: 'rgba(0, 0, 0, 0.18)',
        '& .MuiListItemIcon-root': {
          color: '#fff',
        },
      },
      paddingLeft: ({top}) => top ? 20 : 35,
      background: ({top}) => top ? '' : 'rgba(0, 0, 0, 0.08)',
    },
  }),
)

AppMenuItem.defaultProps = {
  top: true
}

export default AppMenuItem
