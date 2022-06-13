import Card from 'react-bootstrap/Card';
import react from 'react';
import { ComponentStory, ComponentMeta } from '@storybook/react';

export default {
  title: 'Card',
  component: Card,
};

const Template: ComponentStory<typeof Card> = (args) => <Card {...args} />;

export const background = Template.bind({});
background.args = {
  background: 'primary',
};

export const text = Template.bind({});
text.args = {
  text: 'secondary',
};

export const border = Template.bind({});
border.args = {
  border: 'dark',
};

export const body = Template.bind({});
body.args = {
  body: false,
};

export const Cardprops = Template.bind({});
Cardprops.args = {
  Title: 'Hello',
  Subtitle: 'Hello',
  Body: false,
  Link: 'a',
  Text: 'Hello',
  Header: 'Hello',
  Footer: 'Hello',
  ImgOverlay: true,
};
