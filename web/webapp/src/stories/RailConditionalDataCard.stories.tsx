import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { ComponentStory, ComponentMeta } from '@storybook/react';
import { RailConditionDataCard } from '../common/RailConditionDataCard';

export default {
  title: 'Component/Card1',
  component: RailConditionDataCard,
} as ComponentMeta<typeof RailConditionDataCard>;

const Template: ComponentStory<typeof RailConditionDataCard> = (args) => (
  <RailConditionDataCard {...args} />
);

export const HundredPages = Template.bind({});
