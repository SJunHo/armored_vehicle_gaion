import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { ComponentStory, ComponentMeta } from '@storybook/react';
import { Section } from '../common/Section/Section';

export default {
  title: 'Component/Section',
  component: Section,
} as ComponentMeta<typeof Section>;

const Template: ComponentStory<typeof Section> = (args) => (
  <Section {...args} />
);

export const HundredPages = Template.bind({});
